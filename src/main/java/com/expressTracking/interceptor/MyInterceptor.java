package com.expressTracking.interceptor;

import com.expressTracking.exception.MyExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    public MyInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getHeader("session");
        if (sessionId == null) {
            response.sendError(401, "非法访问,请登录");
            return false;
        } else if (request.getSession().getAttribute(sessionId) == null) {
            response.sendError(401, "非法访问,请登录");
            return false;
        }
        return true;
    }
}
