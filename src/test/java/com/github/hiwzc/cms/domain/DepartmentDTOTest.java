package com.github.hiwzc.cms.domain;

import com.github.hiwzc.cms.domain.dto.DepartmentDTO;
import com.github.hiwzc.cms.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DepartmentDTOTest {
    @Test
    public void testDepartmentDTO() {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId("1");
        dto.setCreatedBy("cms");
        dto.setCreatedDate(new Date());
        dto.setUpdatedBy("cms");
        dto.setUpdatedDate(new Date());
        dto.setDepartmentCode("101");
        dto.setDepartmentName("北京");
        System.out.println(JsonUtils.toJsonString(dto));
    }
}
