package com.sse.ftp.service.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Component
public class AsyncDemo {

    @Async
    public void invokeSimplest() {
        log.info("asyncSimplest");
    }

    @Async
    public void invokeWithException(String s) {
        log.info("async invoke with parameter:{}", s);
        int i = 0;
        do {
            try {
                for (; i < 10; i++) {
                    if (i == 5) {
                        throw new IllegalArgumentException(s + i);
                    }
                    log.info("{}", i);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
//                throw e;
            }
        } while (i++ < 10);
    }

    @Async
    public Future<String> invokeWithReturn(int i) {
        log.info("async invoke return future, parameter:{}", i);
        Future<String> future;
        try {
            Thread.sleep(1000);
            future = new AsyncResult<>("success:" + i);
            throw new IllegalArgumentException("a");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            future = new AsyncResult<>("error:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            future = new AsyncResult<>("error:" + e.getMessage());
        }
        return future;
    }
}
