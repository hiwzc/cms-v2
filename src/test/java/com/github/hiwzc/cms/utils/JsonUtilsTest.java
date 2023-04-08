package com.github.hiwzc.cms.utils;

import com.github.hiwzc.cms.base.ResultCode;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtilsTest {
    @Test
    public void jsonTest() {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        String code = ResultCode.SUCCESS.getCode();
        String msg = ResultCode.SUCCESS.getMsg();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        resultMap.put("data", null);
        String json = JsonUtils.toJsonString(resultMap);
        System.out.println(json);
    }
}