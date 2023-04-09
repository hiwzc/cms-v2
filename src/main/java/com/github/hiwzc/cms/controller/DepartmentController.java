package com.github.hiwzc.cms.controller;

import com.github.hiwzc.cms.base.PageInfo;
import com.github.hiwzc.cms.base.Result;
import com.github.hiwzc.cms.domain.dto.DepartmentDTO;
import com.github.hiwzc.cms.domain.qo.DepartmentQO;
import com.github.hiwzc.cms.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping("/search")
    public Result<PageInfo<DepartmentDTO>> searchDepartment(@RequestBody @Valid DepartmentQO form) {
        return Result.success(departmentService.searchDepartment(form));
    }
}
