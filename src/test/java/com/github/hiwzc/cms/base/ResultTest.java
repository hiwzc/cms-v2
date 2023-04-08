package com.github.hiwzc.cms.base;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testSuccessResult() {
        Result<String> result = Result.success();
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(ResultCode.SUCCESS.getMsg(), result.getMsg());
        Assertions.assertNull(result.getData());
    }

    @Test
    public void testSuccessResultWithData() {
        String data = "something data";
        Result<String> result = Result.success(data);
        Assertions.assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        Assertions.assertEquals(ResultCode.SUCCESS.getMsg(), result.getMsg());
        Assertions.assertEquals(data, result.getData());
    }

    @Test
    public void testErrorResult() {
        Result<String> result = Result.error(null);
        Assertions.assertEquals(ResultCode.ERROR.getCode(), result.getCode());
        Assertions.assertEquals(ResultCode.ERROR.getMsg(), result.getMsg());
        Assertions.assertNull(result.getData());
    }

    @Test
    public void testErrorResultWithMsg() {
        String errMsg = "Parameter Invalid";
        Result<String> result = Result.error(errMsg);
        Assertions.assertEquals(ResultCode.ERROR.getCode(), result.getCode());
        Assertions.assertEquals(errMsg, result.getMsg());
        Assertions.assertNull(result.getData());
    }
}
