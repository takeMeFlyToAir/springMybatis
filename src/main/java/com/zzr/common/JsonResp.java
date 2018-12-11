package com.zzr.common;

import java.io.Serializable;

/**
 * Created by qiuyujiang on 2018/11/15.
 */
public class JsonResp implements Serializable{

    public final static Integer SUCCESS = 0;
    public final static Integer FAIL = 1;

    private Integer code;
    private String message;
    private Object result;

    public JsonResp() {
    }

    public JsonResp(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResp(String message) {
        this(SUCCESS,message);
    }

    public JsonResp(Object result ) {
       this(SUCCESS,"",result);
    }

    public JsonResp(Integer code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
