package com.expressTracking.exception;

/**
 * 服务异常
 */
public class ServiceException extends RuntimeException {

//    public final int

    private int code; //异常码

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
