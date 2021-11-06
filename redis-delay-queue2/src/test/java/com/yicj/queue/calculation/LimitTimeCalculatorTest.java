package com.yicj.queue.calculation;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LimitTimeCalculatorTest {

    /**
     * 1. 规则选择禁区时间时，时间不能存在交叉
     * 2. 移动时间必须大于，规则关联的能发消息的时间范围的最后一段（加限定时间 + 修改移动时间都需要校验）
     */


    private LimitTimeCalculator calculator = new LimitTimeCalculator() ;

    @Test
    public void isIntersectionWithDate(){
        DateTime now = DateTime.now();
        boolean flag1 = calculator.isIntersectionWithDate(now, new LimitTimeInfo(
                now.plusHours(1),
                now.plusHours(5)
        ) );
        log.info("flag1 : {}", flag1);
        ///////////////////////////////
        boolean flag2 = calculator.isIntersectionWithDate(now, new LimitTimeInfo(
                now.plusDays(1),
                now.plusDays(5)
        ) );
        log.info("flag2 : {}", flag2);
        ///////////////////////////////
        DateTime dateTime = new DateTime(2021,10,24,23,59,57) ;
        boolean flag3 = calculator.isIntersectionWithDate(dateTime, new LimitTimeInfo(
                dateTime.plusSeconds(1),
                dateTime.plusSeconds(5)
        ));
        log.info("flag3 : {}", flag3);
    }

    // 去除与当天不存在交集的记录
    @Test
    public void filterLimitByCurDate(){
        DateTime dateTime = new DateTime(2021,10,24,12,20,59) ;
        List<LimitTimeInfo> limitList = Arrays.asList(
            new LimitTimeInfo(dateTime.plusDays(1), dateTime.plusDays(4)),
            new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusDays(2)),
            new LimitTimeInfo(dateTime.plusHours(2), dateTime.plusDays(3))
        ) ;
        List<LimitTimeInfo> list = calculator.filterLimitByCurDate(dateTime, limitList);
        list.forEach(item -> log.info("item : {}", item));
    }

    @Test
    public void convertToCurDateTime(){
        DateTime dateTime = new DateTime(2021,10,24,12,20,59) ;
        List<LimitTimeInfo> limitList = Arrays.asList(
                new LimitTimeInfo(dateTime.plusDays(1), dateTime.plusDays(4)),
                new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusDays(2)),
                new LimitTimeInfo(dateTime.plusHours(2), dateTime.plusDays(3))
        ) ;
        List<LimitTimeInfo> list = calculator.filterLimitByCurDate(dateTime, limitList);
        List<LimitTimeInfo> retList = calculator.convertToCurDateTime(dateTime, list);
        retList.forEach(item -> log.info("item : {}", item));
    }

    @Test
    public void orderByStart(){
        DateTime dateTime = new DateTime(2021,10,24,12,20,59) ;
        List<LimitTimeInfo> limitList = Arrays.asList(
                new LimitTimeInfo(dateTime.plusDays(1), dateTime.plusDays(4)),
                new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusDays(2)),
                new LimitTimeInfo(dateTime.plusHours(2), dateTime.plusDays(3))
        ) ;
        List<LimitTimeInfo> filterList = calculator.filterLimitByCurDate(dateTime, limitList);
        List<LimitTimeInfo> convertList = calculator.convertToCurDateTime(dateTime, filterList);
        List<LimitTimeInfo> orderList = calculator.orderByStart(convertList);
        orderList.forEach(item -> log.info("info : {}", item));
    }

    @Test
    public void orderByStart2(){
        DateTime dateTime = new DateTime(2021,10,24,0,0,1) ;
        LimitTimeInfo info1 = new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusHours(12)) ;
        LimitTimeInfo info2 = new LimitTimeInfo(dateTime.plusHours(5), dateTime.plusHours(7)) ;
        LimitTimeInfo info3 = new LimitTimeInfo(dateTime.plusHours(10), dateTime.plusHours(12)) ;
        LimitTimeInfo info4 = new LimitTimeInfo(dateTime.plusHours(18), dateTime.plusHours(23)) ;
        List<LimitTimeInfo> list = new ArrayList<>() ;
        list.add(info4) ;
        list.add(info2) ;
        list.add(info1) ;
        list.add(info3) ;
    }

    @Test
    public void mergeLimitTimeInfo(){
        DateTime dateTime = new DateTime(2021,10,24,12,20,59) ;
        LimitTimeInfo info1 = new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusHours(6)) ;
        LimitTimeInfo info2 = new LimitTimeInfo(dateTime.plusHours(5), dateTime.plusHours(7)) ;
        List<LimitTimeInfo> list = calculator.mergeLimitTimeInfo(info1, info2);
        list.forEach(item -> log.info("info : {}", item));
    }

    @Test
    public void mergeLimitList(){
        DateTime dateTime = new DateTime(2021,10,24,0,0,1) ;
        LimitTimeInfo info1 = new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusHours(21)) ;
        LimitTimeInfo info2 = new LimitTimeInfo(dateTime.plusHours(5), dateTime.plusHours(7)) ;
        LimitTimeInfo info3 = new LimitTimeInfo(dateTime.plusHours(10), dateTime.plusHours(12)) ;
        LimitTimeInfo info4 = new LimitTimeInfo(dateTime.plusHours(18), dateTime.plusHours(23)) ;
        List<LimitTimeInfo> list = new ArrayList<>() ;
        list.add(info1) ;
        list.add(info2) ;
        list.add(info3) ;
        list.add(info4) ;
        List retList = calculator.mergeLimitList(list, new ArrayList<>(), new ArrayList<>());
        retList.forEach(item -> log.info("item : {}", item));
    }

    @Test
    public void calculate(){
        DateTime dateTime = new DateTime(2021,10,24,0,0,1) ;
        LimitTimeInfo info1 = new LimitTimeInfo(dateTime.plusHours(3), dateTime.plusHours(12)) ;
        LimitTimeInfo info2 = new LimitTimeInfo(dateTime.plusHours(5), dateTime.plusHours(7)) ;
        LimitTimeInfo info3 = new LimitTimeInfo(dateTime.plusHours(10), dateTime.plusHours(12)) ;
        LimitTimeInfo info4 = new LimitTimeInfo(dateTime.plusHours(18), dateTime.plusHours(23)) ;
        List<LimitTimeInfo> list = new ArrayList<>() ;
        list.add(info4) ;
        list.add(info2) ;
        list.add(info1) ;
        list.add(info3) ;
        List retList = calculator.calculate(DateTime.now(), list);
        retList.forEach(item -> log.info("item : {}", item));
    }
}
