package com.sse.ftp.filter;

import com.alibaba.fastjson.JSON;

import com.sse.ftp.domain.Result4Object;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ValidFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ValidFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        if (uri.contains("?"))
            uri = uri.substring(0, uri.indexOf("?"));
        if (uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".ico")
                || uri.endsWith(".jpg") || uri.endsWith(".gif")
                || uri.endsWith(".jpeg") || uri.endsWith(".html")
                || uri.contains("swagger") || uri.contains("api-docs")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String username = request.getHeader("username");
        if (null == username) {
            username = request.getParameter("username");
        }
        if (null == username) {
            responseFail((HttpServletResponse) servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void responseFail(HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");

        Result4Object result4Object = Result4Object.builder().code(403).message("Forbidden Visit").build();
        log.info(result4Object.toString());
        String json = JSON.toJSONString(result4Object);
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
