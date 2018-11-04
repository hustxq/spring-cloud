package com.sse.ftp.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @aurhor xghe
 * @Date:2017-11-25
 * @description 统一日志拦截输出类
 */
@Aspect
@Component
@Order(-1)
public class LogAspectConfig {
    private Logger logger = LoggerFactory.getLogger(LogAspectConfig.class);
    /**
     * 记录请求开始时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.sse.ftp.controller..*.*(..)) "
            + "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        // 记录下请求内容
        logger.info("请求Id[" + session.getId() +
                "]; 请求地址[ " + request.getRequestURL().toString() +
                "]; 请求方法[" + request.getMethod() +
                "]; IP地址[" + request.getRemoteAddr() +
                "]; 调用方法[" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() +
                "]; 传入参数[" + str + "];");
    }

    @AfterReturning(returning = "resResult", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object resResult) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        // 处理完请求，返回内容
        logger.info("请求Id[" + session.getId() +
                "]; 响应内容[" + JSON.toJSONString(resResult) +
                "]; 请求耗时[" + (System.currentTimeMillis() - startTime.get()) / 1000.0 + "s];");
        startTime.remove();
    }


}
