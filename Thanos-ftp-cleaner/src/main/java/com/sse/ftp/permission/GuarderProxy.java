package com.sse.ftp.permission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: qxiong
 * @date: 2018/10/24
 * @description:
 */

@Component
@Aspect
@Order()
public class GuarderProxy {
    private Logger logger = LoggerFactory.getLogger(GuarderProxy.class);

    @Pointcut("execution(public * com.sse.ftp.controller..*.*(..)) "
            + "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void check() {
    }

    @Around("check()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable {
        Class target = point.getTarget().getClass();
        Guarder clsGuarder = (Guarder) target.getAnnotation(Guarder.class);
        Set<Permission> set = new HashSet<>();
        if (null != clsGuarder) {
            Permission[] ps = clsGuarder.name();
            for (Permission p : ps) {
                set.add(p);
            }
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
//        get annotation params
        Guarder guarder = method.getAnnotation(Guarder.class);
        if (null != guarder) {
            Permission[] ps = guarder.name();
            for (Permission p : ps) {
                set.add(p);
            }
        }

        logger.info(set.toString());

        if (set.isEmpty()) {
            return point.proceed();
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String role = request.getHeader("role");

        if (null != role) {
            boolean flag = false;
            for (Permission p : set) {
                if (p.getV().equalsIgnoreCase(role)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return point.proceed();
            } else {
                throw new PermissionException("Forbidden");
            }
        }

        throw new PermissionException("\'@Guarder\' must have Parameter \'role\'.");
    }
}
