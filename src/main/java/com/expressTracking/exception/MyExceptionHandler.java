package com.expressTracking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletRequest;

/**
 * @author muwei
 */

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);
    
    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<String> exp(Exception e, HttpServletRequest request) {
        LOGGER.error(request.getRequestURI() + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Type", "Error")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"message\":\"" + e.getMessage() + "\"}");
    }

}
