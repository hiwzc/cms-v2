package com.github.hiwzc.cms.base;

public enum ResultCode {
    SUCCESS("0000", "ok"),
    ERROR("9999", "SYSTEM ERROR");

    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
