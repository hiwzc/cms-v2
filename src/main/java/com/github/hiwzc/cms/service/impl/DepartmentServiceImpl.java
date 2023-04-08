package com.github.hiwzc.cms.service.impl;

import com.github.hiwzc.cms.base.PageInfo;
import com.github.hiwzc.cms.domain.dto.DepartmentDTO;
import com.github.hiwzc.cms.domain.qo.DepartmentQO;
import com.github.hiwzc.cms.mapper.DepartmentMapper;
import com.github.hiwzc.cms.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public PageInfo<DepartmentDTO> searchDepartment(DepartmentQO form) {
        PageHelper.startPage(form.getPageNum(), form.getPageSize());
        com.github.hiwzc.cms.domain.qo.DepartmentQO qo = new com.github.hiwzc.cms.domain.qo.DepartmentQO();
        BeanUtils.copyProperties(form, qo);
        return new PageInfo<>(departmentMapper.searchDepartment(qo));
    }
}
