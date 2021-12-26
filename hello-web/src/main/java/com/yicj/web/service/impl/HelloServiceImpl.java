package com.yicj.web.service.impl;

import com.yicj.web.service.HelloService;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) throws Exception{
        int executorTimeout = 20 ;
        Thread futureThread = null;
        try {
            FutureTask<Boolean> futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // init job context
                    //XxlJobContext.setXxlJobContext(xxlJobContext);
                    //handler.execute();
                    // todo busi
                    return true;
                }
            });
            futureThread = new Thread(futureTask);
            futureThread.start();

            Boolean tempResult = futureTask.get(executorTimeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            //XxlJobHelper.log("<br>----------- xxl-job job execute timeout");
            //XxlJobHelper.log(e);
            // handle result
            //XxlJobHelper.handleTimeout("job execute timeout ");
        } finally {
            futureThread.interrupt();
        }
        return null;
    }


}
