package com.github.hiwzc.cms.domain.dto;

import com.github.hiwzc.cms.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO extends BaseDTO {
    private String departmentCode;
    private String departmentName;
}
