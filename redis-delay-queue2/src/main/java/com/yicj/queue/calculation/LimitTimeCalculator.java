package com.yicj.queue.calculation;

import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LimitTimeCalculator {

    public List<LimitTimeInfo> calculate(DateTime curDate, List<LimitTimeInfo> limitList){
        //1. 过滤与当天没关系的数据
        List<LimitTimeInfo> filterList = this.filterLimitByCurDate(curDate, limitList);
        //2. 讲日期都转化为当天的日期
        List<LimitTimeInfo> convertList = this.convertToCurDateTime(curDate, filterList);
        //3. 对时间按照起始时间进行排序
        List<LimitTimeInfo> orderList = this.orderByStart(convertList) ;
        //4. 对存在交集的数据进行合并处理
        return this.mergeLimitList(orderList, new ArrayList<>(), new ArrayList<>()) ;
    }

    // 讲数据合并去并集
    public List<LimitTimeInfo> mergeLimitList(List<LimitTimeInfo> list ,List<LimitTimeInfo> retList, List<LimitTimeInfo> lastList) {
        // 1. 如果待合并的集合元素为空，则直接返回
        if (CollectionUtils.isEmpty(list)){
            return retList ;
        }
        //0 表示首次进行合并
        if (lastList.size() == 0){
            // 首次合并，且只有一个元素则直接返回
             if (list.size() ==1){
                 retList.add(list.get(0)) ;
                 list.remove(0) ;
                 return retList ;
             }else {
                 // 首次合并，且不止一个元素
                 LimitTimeInfo info1 = list.get(0) ;
                 LimitTimeInfo info2= list.get(1) ;
                 list.remove(info1) ;
                 list.remove(info2) ;
                 List<LimitTimeInfo> mergeList = this.mergeLimitTimeInfo(info1, info2);
                 retList.addAll(mergeList) ;
                 return this.mergeLimitList(list, retList, mergeList) ;
             }
        }else {
            if (lastList.size() == 1){
                // 上次元素存在交集且已经合并，那只需要取一个元素进行下次合并
                LimitTimeInfo info1 = lastList.get(0);
                LimitTimeInfo info2 = list.get(0);
                retList.remove(info1) ;
                // retList里面删除本次参与计算合并的元素
                list.remove(info2) ;
                List<LimitTimeInfo> mergeList = this.mergeLimitTimeInfo(info1, info2);
                retList.addAll(mergeList) ;
                return this.mergeLimitList(list, retList, mergeList) ;
            }else{
                // 上次合并不存在交接，则取上次合并后面那个元素与后面的元素进行合并
                LimitTimeInfo info1 = lastList.get(1) ;
                LimitTimeInfo info2 = list.get(0);
                // retList里面删除本次参与计算合并的元素
                retList.remove(info1) ;
                list.remove(info2) ;
                List<LimitTimeInfo> mergeList = this.mergeLimitTimeInfo(info1, info2);
                retList.addAll(mergeList) ;
                return this.mergeLimitList(list, retList, mergeList) ;
            }
        }
    }


    public List<LimitTimeInfo> mergeLimitTimeInfo(LimitTimeInfo info, LimitTimeInfo successor){
        List<LimitTimeInfo> retList = new ArrayList<>() ;
        DateTime infoEnd = info.getEnd() ;
        DateTime successorStart = successor.getStart() ;
        // 如果两端时间存在交集
        if (infoEnd.isAfter(successorStart) || infoEnd.isEqual(successorStart)){
            //如果info的截止日期大于successor的截止日期
            if (info.getEnd().isAfter(successor.getEnd()) || info.getEnd().isEqual(successor.getEnd())){
                retList.add(info) ;
            }else {
                retList.add(new LimitTimeInfo(info.getStart(), successor.getEnd())) ;
            }
        }else {
            // 如果不存在交集，则直接将两端时间都返回去
            retList.add(info) ;
            retList.add(successor) ;
        }
        return retList ;
    }

    public List<LimitTimeInfo> orderByStart(List<LimitTimeInfo> curList){
         curList.sort((info, other) ->{
            if (info.getStart().isEqual(other.getStart())){
                return 0 ;
            }
            if (info.getStart().isBefore(other.getStart())){
                return -1;
            }
            return 1 ;
         });
         return curList ;
    }

    public List<LimitTimeInfo> convertToCurDateTime(DateTime curDate, List<LimitTimeInfo> limitList){
        int year = curDate.getYear();
        int month = curDate.getMonthOfYear() ;
        int day = curDate.getDayOfMonth() ;
        return limitList.stream().map(item ->{
            DateTime start = item.getStart();
            DateTime end = item.getEnd();
            DateTime curStart = new DateTime(year, month, day,
                    start.getHourOfDay(), start.getMinuteOfHour(), start.getSecondOfMinute()) ;
            DateTime curEnd = new DateTime(year, month, day,
                    end.getHourOfDay(), end.getMinuteOfHour(), end.getSecondOfMinute()) ;
            return new LimitTimeInfo(curStart, curEnd) ;
        }).collect(Collectors.toList()) ;
    }


    /**
     * 去除与当天不存在交集的记录
     * @param curDate
     * @param limitList
     * @return
     */
    public List<LimitTimeInfo> filterLimitByCurDate(DateTime curDate, List<LimitTimeInfo> limitList){
        return limitList.stream().filter(item ->{
            DateTime start = item.getStart();
            DateTime end = item.getEnd();
            return isIntersectionWithDate(curDate, new LimitTimeInfo(start, end)) ;
        }).collect(Collectors.toList());
    }

    // 判断限制时间与当天23:59:59是否存在交集
    public boolean isIntersectionWithDate(DateTime dateTime, LimitTimeInfo info){
        DateTime curDateStart = new DateTime(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth(),
                0,
                0,
                0
        ) ;
        // 当天的截止时间
        DateTime curDateEnd = new DateTime(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth(),
                23,
                59,
                59
        ) ;
        DateTime start = info.getStart();
        DateTime end = info.getEnd();
        // 如果起始日期与截止日期倒挂，则直接返回false
        if (start.isAfter(end) || start.isEqual(end)){
            return false ;
        }
        // 当天截止日期大于 日期范围起始日期 且 当天起始日期 小于 日期范围的截止日期，则说明存在交接
        if (curDateEnd.isAfter(start) && curDateStart.isBefore(end)){
            return true ;
        }
        return false ;
    }

}
