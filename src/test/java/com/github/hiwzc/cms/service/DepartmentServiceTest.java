package com.github.hiwzc.cms.service;

import com.github.hiwzc.cms.domain.qo.DepartmentQO;
import com.github.hiwzc.cms.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testQuery() {
        DepartmentQO form = new DepartmentQO();
        form.setPageSize(5);
        form.setPageNum(3);
        System.out.println(JsonUtils.toJsonString(departmentService.searchDepartment(form)));
    }
}
