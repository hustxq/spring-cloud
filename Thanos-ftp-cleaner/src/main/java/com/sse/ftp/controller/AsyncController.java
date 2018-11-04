package com.sse.ftp.controller;

import com.sse.ftp.domain.Result4Object;
import com.sse.ftp.service.async.AsyncDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {
    @Autowired
    private AsyncDemo asyncDemo;

    @RequestMapping("/simplest")
    public Object simplest() {
        asyncDemo.invokeSimplest();
        return new Result4Object();
    }

    @RequestMapping("/exp")
    public Object exp() {
        asyncDemo.invokeWithException("test");
        return new Result4Object();
    }

}
