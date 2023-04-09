# 统一异常处理、JSON格式、参数校验

## 新增依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## 增加参数错误返回码

ResultCode.java

```java
PARAM_ERROR("0001", "参数错误"),
```

## Result工具类支持传code和msg

```java
public static <T> Result<T> create(String code, String msg) {
    Result<T> result = new Result<>();
    result.setCode(code);
    if (msg != null && msg.length() > 0) {
        result.setMsg(msg);
    } else {
        result.setMsg(ResultCode.ERROR.getMsg());
    }
    return result;
}
```

## 统一 JSON 格式

统一将JSON日期格式，并忽略返回中的空字段

application.properties

```properties
# Date Format
spring.jackson.date-format =yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8
spring.jackson.default-property-inclusion=non_null
```

JsonUtils.java

```java
mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
```

## 全局异常处理

```java
package com.github.hiwzc.cms.config;

import com.github.hiwzc.cms.base.Result;
import com.github.hiwzc.cms.base.ResultCode;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> errorHandler(MethodArgumentNotValidException exception) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(":");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
            msg.append("; ");
        }
        msg.setLength(msg.length() - 2);
        return Result.create(ResultCode.PARAM_ERROR.getCode(), msg.toString());
    }

}
```

## 增加校验注解

DepartmentQO.java

```java
package com.github.hiwzc.cms.domain.qo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DepartmentQO {
    @NotNull
    @Min(1)
    private Integer pageNum;

    @NotNull
    @Min(1)
    private Integer pageSize;

    @Size(min = 1, max = 10)
    private String departmentCode;
}
```

DepartmentController.java

```java
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

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping("/search")
    public Result<PageInfo<DepartmentDTO>> searchDepartment(@RequestBody @Valid DepartmentQO form) {
        return Result.success(departmentService.searchDepartment(form));
    }
}
```