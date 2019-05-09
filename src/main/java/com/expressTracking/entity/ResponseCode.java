package com.expressTracking.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.expressTracking.utils.JsonUtils;
import com.google.gson.annotations.Expose;

/**
 * @ClassName ResponseCode
 * @Decsription TODO
 * @Author liwei
 * @Date 2019/5/6 17:51
 * @Version 1.0
 */
public class ResponseCode {


    private int type; //操作类型

    @Expose
    private int code = 0;

    @Expose
    private String message = null;

    public ResponseCode(int type, int code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public ResponseCode() {
    }

    public ResponseCode(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String toJson(ResponseCode responseCode) {
        return JsonUtils.toJson(responseCode);
    }

    public class Result {
        public static final int SUCESS = 0;
        public static final int FAIL = 1;
        public static final int ERROR = 2;
    }

    public class Type {
        public static final int ADD = 1;
        public static final int DELETE = 2;
        public static final int UPDATE = 3;
        public static final int SELECT = 4;
    }
}
