package com.github.hiwzc.cms.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PageInfoTest {
    @Test
    public void testPageInfo() {
        int pageNum = 1;
        int pageSize = 10;
        int total = 20;
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

        PageInfo<Integer> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(total);
        pageInfo.setList(list);

        Assertions.assertEquals(pageInfo.getPageNum(), pageNum);
        Assertions.assertEquals(pageInfo.getPageSize(), pageSize);
        Assertions.assertEquals(pageInfo.getTotal(), total);
        Assertions.assertEquals(pageInfo.getList(), list);
    }
}
