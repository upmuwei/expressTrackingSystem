package com.expressTracking.exception;

/**
 * 服务异常
 */
public class ServiceException extends RuntimeException {


    private int code; //异常码

    public ServiceException(int code) {
        this.code = code;
    }

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

    public static String getServiceExceptionMsg(ServiceException serviceException) {

        switch (serviceException.getCode()) {
            case 1000:
                return "用户不存在 " + serviceException.getMessage();
            case 1002:
                return "账号或密码错误 " + serviceException.getMessage();
            case 2000:
                return "快件不存在 " + serviceException.getMessage();
            case 2001:
                return "快件状态错误 " + serviceException.getMessage();
            case 3000:
                return "包裹不存在 " + serviceException.getMessage();
            case 3001:
                return "包裹状态错误 " + serviceException.getMessage();
            case 4000:
                return "用户没有正在转运的包裹 " + serviceException.getMessage();
            case 4001:
                return "用户已转运该包裹 " + serviceException.getMessage();
            case 3002:
                return "包裹没有被转运 " + serviceException.getMessage();
            default:
                return serviceException.getMessage();
        }

    }
}
