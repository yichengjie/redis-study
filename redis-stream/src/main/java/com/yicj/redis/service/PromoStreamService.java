package com.yicj.redis.service;

import com.yicj.redis.constants.CommonConstant;
import com.yicj.redis.model.PromoUserTaskDTO;
import com.yicj.redis.model.PromoUserTaskVO;

import java.util.List;

public interface PromoStreamService {

    String STREAM_NAME = CommonConstant.PROMO_POSTER_TASK_STREAM_KEY;

    String addTask(PromoUserTaskDTO dto) ;

    List<PromoUserTaskVO> listAllTask() ;
}
