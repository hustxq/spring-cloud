package com.sse.ftp.controller;

import com.sse.ftp.domain.Result4Object;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: qxiong
 * @date: 2018/10/24
 * @description:
 */
@RestController
public class NoPermissionController {

    @RequestMapping("/no")
    public Object no(HttpServletRequest request) {
        return new Result4Object().builder().message("no permission guarder");
    }

}
