package com.yicj.redis.controller;

import com.yicj.redis.model.PromoRequest;
import com.yicj.redis.model.PromoUserDTO;
import com.yicj.redis.model.PromoUserVo;
import com.yicj.redis.service.PromoStreamService;
import com.yicj.redis.utils.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PromotionController {

    private final PromoStreamService promoStreamService ;
    private final RedisUtil redisUtil ;

    public PromotionController(PromoStreamService promoStreamService, RedisUtil redisUtil) {
        this.promoStreamService = promoStreamService;
        this.redisUtil = redisUtil ;
    }

    @PostMapping("/gen")
    public List<String> hello(@RequestBody PromoRequest request){
        List<String> retList = new ArrayList<>() ;
        request.getOwnerList()
            .stream()
            .forEach(userCode ->{
                PromoUserDTO vo = new PromoUserDTO(request.getPromoId(), userCode) ;
                String id = promoStreamService.addTask(vo);
                retList.add(id) ;
            });
        return retList ;
    }

    @GetMapping("/list")
    public List<PromoUserVo> list(){
        return promoStreamService.listAllTask();
    }

    @GetMapping("/hello")
    public String hello(){
        redisUtil.set("hello", "world");
        return "hello" ;
    }
}
