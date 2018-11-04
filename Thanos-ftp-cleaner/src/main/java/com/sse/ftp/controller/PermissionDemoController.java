package com.sse.ftp.controller;

import com.sse.ftp.domain.Result4Object;
import com.sse.ftp.permission.Guarder;
import com.sse.ftp.permission.Permission;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: qxiong
 * @date: 2018/10/24
 * @description:
 */
@Slf4j
@Guarder
@RestController
public class PermissionDemoController {
//    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Guarder(name = {Permission.OPERATOR, Permission.ADMIN})
    @RequestMapping("/demo")
    public Object demo(HttpServletRequest request) {
        log.info("{}", "test log.");
        log.info("{}", "test debug");
        return new Result4Object().builder().message("test permission").build();
    }

    @RequestMapping("/de")
    public Object no(HttpServletRequest request) {
        return new Result4Object().builder().message("no permission guarder");
    }
}
