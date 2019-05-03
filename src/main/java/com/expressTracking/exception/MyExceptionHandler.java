package com.expressTracking.exception;

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


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author muwei
 * @date 2019/5/3
 */

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);
    
    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<String> exp(Exception e, HttpServletRequest request) {
        System.out.println(e.getClass().getName());
        LOGGER.error(request.getRequestURI() + e.getMessage());
        if (e instanceof SQLException || e instanceof ReflectionException) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Type", "Error")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("{\"message\":\"操作失败，发生异常\"}");
        } else if (e instanceof HttpMessageNotReadableException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Type", "Error")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body("{\"message\":\"数据格式转换错误\"}");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Type", "Error")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"" + e.getMessage() + "\"}");
    }

}
