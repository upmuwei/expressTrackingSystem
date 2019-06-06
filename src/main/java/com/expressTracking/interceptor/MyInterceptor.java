package com.expressTracking.interceptor;

import com.alibaba.fastjson.JSON;
import com.expressTracking.entity.ResponseCode;
import com.expressTracking.exception.GlobalExceptionHandler;
import com.expressTracking.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author muwei
 * @date 2019/5/28
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public MyInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getHeader("sessionId");
       /*
        ResponseCode code = new ResponseCode();
        if (sessionId == null || request.getSession().getAttribute(sessionId) == null) {
            code.setCode(2);
            code.setMessage("非法访问");
            PrintWriter writer = response.getWriter();
            writer.print(JsonUtils.toJson(code));
            response.setContentType("application/json;charset=UTF-8");
            LOGGER.error(request.getRequestURI() + "非法访问");
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}