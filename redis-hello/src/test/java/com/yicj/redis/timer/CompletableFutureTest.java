package com.yicj.redis.timer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureTest {


    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            log.info("=======> return value : {}", 1);
        });
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            log.info("=======> return value : {}", 2);
        });
        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> {
            log.info("=======> return value : {}", 3);
        });
        CompletableFuture<Void> cf4 = CompletableFuture.runAsync(() -> {
            log.info("=======> return value : {}", 4);
        });
        CompletableFuture<Void> cf5 = CompletableFuture.runAsync(() -> {
            log.info("=======> return value : {}", 5);
        });

        CompletableFuture<Void> future = CompletableFuture.allOf(cf1, cf2, cf3, cf4, cf5).whenComplete((t, e) -> {
            log.info("=======> value : {}", t);
        });
        future.get() ;
    }
}
