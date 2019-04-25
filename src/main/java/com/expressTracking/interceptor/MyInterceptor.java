package com.expressTracking.interceptor;

import com.expressTracking.exception.MyExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    public MyInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /* String sessionId = request.getHeader("session");
        if (sessionId == null) {
            PrintWriter writer = response.getWriter();
            writer.print("{\"message\":\"非法访问,请登录\"}");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            LOGGER.error(request.getRequestURI() + "非法访问");
            return false;
        } else if (request.getSession().getAttribute(sessionId) == null) {
            PrintWriter writer = response.getWriter();
            writer.print("{\"message\":\"非法访问,请登录\"}");
            response.setContentType("application/json;charset=UTF-8");
            response.sendError(401, "非法访问,请登录");
            LOGGER.error(request.getRequestURI() + "非法访问");
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
