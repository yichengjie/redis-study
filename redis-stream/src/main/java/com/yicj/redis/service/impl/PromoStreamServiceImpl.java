package com.yicj.redis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yicj.redis.model.PromoUserTaskDTO;
import com.yicj.redis.model.PromoUserTaskVO;
import com.yicj.redis.support.stream.impl.AbstractStreamService;
import com.yicj.redis.support.stream.StreamInfoVo;
import com.yicj.redis.service.PromoStreamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("promoStreamServiceImpl")
public class PromoStreamServiceImpl extends AbstractStreamService implements PromoStreamService {

    private final StringRedisTemplate stringRedisTemplate ;

    public PromoStreamServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String addTask(PromoUserTaskDTO dto) {
        Map<String, Object> mapValue = BeanUtil.beanToMap(dto);
        return this.add(STREAM_NAME, mapValue);
    }

    @Override
    public List<PromoUserTaskVO> listAllTask() {
        List<StreamInfoVo> list = this.list(STREAM_NAME);
        if (CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST ;
        }
        return list.stream()
                .map(this::convert2PromoUserVo)
                .filter(this::checkPromoUserVoNotEmpty)
                .collect(Collectors.toList()) ;
    }


    private PromoUserTaskVO convert2PromoUserVo(StreamInfoVo item){
        String id = item.getId();
        Map<Object, Object> map = item.getValue();
        PromoUserTaskVO promoUserVo = new PromoUserTaskVO();
        promoUserVo.setMsgId(id);
        return BeanUtil.fillBeanWithMap(map, promoUserVo, true);
    }

    private boolean checkPromoUserVoNotEmpty(PromoUserTaskVO vo){
        if (StringUtils.isNotBlank(vo.getPromoId()) && StringUtils.isNotBlank(vo.getUserCode())){
            return true ;
        }
        return false ;
    }

    @Override
    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

}
