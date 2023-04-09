package com.github.hiwzc.cms.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.ERROR.getCode());
        if (msg != null && msg.length() > 0) {
            result.setMsg(msg);
        } else {
            result.setMsg(ResultCode.ERROR.getMsg());
        }
        return result;
    }

    public static <T> Result<T> create(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        if (msg != null && msg.length() > 0) {
            result.setMsg(msg);
        } else {
            result.setMsg(ResultCode.ERROR.getMsg());
        }
        return result;
    }
}
