package com.yicj.redis.service;

import com.yicj.redis.constants.CommonConstant;
import com.yicj.redis.model.PromoUserDTO;
import com.yicj.redis.model.PromoUserVo;

import java.util.List;

public interface PromoStreamService {

    String STREAM_NAME = CommonConstant.PROMO_POSTER_TASK_STREAM_KEY;

    String addTask(PromoUserDTO dto) ;

    List<PromoUserVo> listAllTask() ;
}
