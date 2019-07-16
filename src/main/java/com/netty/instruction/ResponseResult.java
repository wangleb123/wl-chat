package com.netty.instruction;

import lombok.Builder;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 12:32
 * @description：统一定义返回值对象
 * @modified By：
 * @version: V1.0.0$
 */
@Builder
public class ResponseResult extends HashMap<String, Object> {

    private static final Integer ERROR_STATUS = -1;

    private static final Integer SUCCESS_STATUS = 200;

    private static final String SUCCESS_MSG = "ok";

    public ResponseResult() {
        super();
    }

    public ResponseResult(int code) {
        super();
        setStatus(code);
    }

    public ResponseResult(HttpStatus status) {
        super();
        setStatus(status.value());
        setMsg(status.getReasonPhrase());
    }

    public ResponseResult success() {
        put("msg", SUCCESS_MSG);
        put("status", SUCCESS_STATUS);
        return this;
    }

    public ResponseResult success(String msg) {
        put("msg", msg);
        put("status", SUCCESS_STATUS);
        return this;
    }

    public ResponseResult error(String msg) {
        put("msg", msg);
        put("status", ERROR_STATUS);
        return this;
    }

    public ResponseResult setData(String key, Object obj) {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> data = (HashMap<String, Object>) get("data");
        if (data == null) {
            data = new HashMap<>();
            put("data", data);
        }
        data.put(key, obj);
        return this;
    }

    public ResponseResult setStatus(int status) {
        put("status", status);
        return this;
    }

    public ResponseResult setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public ResponseResult setValue(String key, Object val) {
        put(key, val);
        return this;
    }





}
