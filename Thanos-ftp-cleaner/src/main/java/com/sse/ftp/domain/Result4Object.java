package com.sse.ftp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Result4Object {
    private int code = 200;
    private String message = "success";
    private Object data;

    @Tolerate
    public Result4Object() {
    }
/*
    public int getCode() {
        return code;
    }

    public Result4Object<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result4Object<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result4Object<T> setData(T data) {
        this.data = data;
        return this;
    }*/
}
