package com.expressTracking.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.entity.ResponseCode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.ibatis.reflection.ReflectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author muwei
 * @date 2019/5/3
 */

@ControllerAdvice
public class GlobalExceptionHandler  {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = {Exception.class,ServiceException.class})
    public JSONObject exp(Exception e, HttpServletRequest request) {
        System.out.println(e.getClass().getName());
        LOGGER.error(request.getRequestURI() + e.getMessage());
        JSONObject jsonObject = new JSONObject();
        ResponseCode code = new ResponseCode();
        code.setCode(ResponseCode.Result.ERROR);
        if (e instanceof SQLException || e instanceof ReflectionException) {
            code.setMessage("数据库操作失败");
        } else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            code.setMessage(ServiceException.getServiceExceptionMsg(serviceException));
        } else {
            code.setMessage(e.getMessage());
        }
        jsonObject.put("code",code);
        return jsonObject;
    }

}
