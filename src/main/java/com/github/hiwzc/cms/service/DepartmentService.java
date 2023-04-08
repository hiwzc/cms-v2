package com.github.hiwzc.cms.service;

import com.github.hiwzc.cms.base.PageInfo;
import com.github.hiwzc.cms.domain.dto.DepartmentDTO;
import com.github.hiwzc.cms.domain.qo.DepartmentQO;

public interface DepartmentService {
    PageInfo<DepartmentDTO> searchDepartment(DepartmentQO form);
}
