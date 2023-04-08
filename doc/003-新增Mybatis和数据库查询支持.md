# 新增Mybatis和数据库查询支持

## 引入依赖

### Mybatis

https://github.com/mybatis/spring-boot-starter

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.3.0</version>
</dependency>
```

### PageHelper分页

https://pagehelper.github.io/

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.6</version>
</dependency>
```

### Druid 连接池

https://github.com/alibaba/druid

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.15</version>
</dependency>
```

### H2 数据库

http://www.h2database.com/html/main.html

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

## application.properties 新增配置

```properties
# DataSource
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:cms;mode=mysql
spring.datasource.username=sa
spring.datasource.password=123456
spring.datasource.druid.initial-size=1
spring.datasource.druid.max-active=10
spring.datasource.druid.min-idle=3
spring.datasource.druid.connect-timeout=2000
spring.datasource.druid.socket-timeout=3000
spring.datasource.druid.max-wait=5000
spring.datasource.druid.test-while-idle=true
spring.datasource.druid.validation-query=select 1
spring.datasource.druid.validation-query-timeout=2

# PageHelper
pagehelper.helper-dialect=h2
pagehelper.reasonable=true
pagehelper.support-methods-arguments=true
pagehelper.params=count=countSql

# Mybatis
mybatis.mapper-locations=classpath:/mapper/*Mapper.xml
mybatis.type-aliases-package=com.github.hiwzc.cms.domain
mybatis.configuration.map-underscore-to-camel-case=true

# Druid Monitor
spring.datasource.druid.filters=stat,wall
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.login-username=druid
spring.datasource.druid.stat-view-servlet.login-password=druid123
spring.datasource.druid.stat-view-servlet.reset-enable=true
spring.datasource.druid.filter.stat.enabled=true
spring.datasource.druid.filter.stat.db-type=mysql
spring.datasource.druid.filter.stat.merge-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=3000
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=mysql
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false

# DataBase
spring.h2.console.enabled=true
spring.sql.init.mode=embedded
spring.sql.init.schema-locations=classpath:/sql/schema.sql
spring.sql.init.data-locations=classpath:/sql/data.sql
```

## Mybatis配置类

```java
package com.github.hiwzc.cms.config;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan("com.github.hiwzc.cms.mapper")
public class MybatisConfig {
}
```

## 分页结果PageInfo支持PageHelper入参

```java
package com.github.hiwzc.cms.base;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageInfo<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private List<T> list;

    public PageInfo(List<T> data) {
        if (data != null) {
            this.list = data;
            if (data instanceof Page) {
                Page<T> page = (Page<T>) data;
                this.pageNum = page.getPageNum();
                this.pageSize = page.getPageSize();
                this.total = (int) page.getTotal();
            } else {
                this.pageNum = 1;
                this.pageSize = data.size();
                this.total = data.size();
            }
        }
    }
}
```

## 域对象

### 数据传输对象 DepartmentDTO

```java
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
```

### 数据查询对象 DepartmentQO

```java
package com.github.hiwzc.cms.domain.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentQO {
    private int pageNum;
    private int pageSize;
    private String departmentCode;
}
```

## Mybatis Mapper

### DepartmentMapper.java

```java
package com.github.hiwzc.cms.mapper;

import com.github.hiwzc.cms.domain.qo.DepartmentQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<DepartmentDTO> searchDepartment(DepartmentQO param);
}
```

### DepartmentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.github.hiwzc.cms.mapper.DepartmentMapper">
    <select id="searchDepartment" parameterType="DepartmentQO" resultType="DepartmentDTO">
        SELECT ID,
               CREATED_BY,
               CREATED_DATE,
               UPDATED_BY,
               UPDATED_DATE,
               DEPARTMENT_CODE,
               DEPARTMENT_NAME
          FROM DEPARTMENT_INFO
         WHERE 1=1
        <if test="departmentCode != null and departmentCode != ''">
           AND DEPARTMENT_CODE = #{departmentCode, jdbcType=VARCHAR}
        </if>
         ORDER BY DEPARTMENT_CODE
    </select>
</mapper>
```

## Service

### 接口 DepartmentService.java

```java
package com.github.hiwzc.cms.service;

import com.github.hiwzc.cms.base.PageInfo;
import com.github.hiwzc.cms.domain.dto.DepartmentDTO;
import com.github.hiwzc.cms.domain.qo.DepartmentQO;

public interface DepartmentService {
    PageInfo<DepartmentDTO> searchDepartment(DepartmentQO form);
}
```

### 实现 DepartmentServiceImpl.java

```java
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
    private DepartmentMapper departmentMapper;

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
```

## SQL 初始化语句

### schema.sql

```sql
DROP TABLE IF EXISTS DEPARTMENT_INFO;
CREATE TABLE DEPARTMENT_INFO (
   ID                           INT          NOT NULL AUTO_INCREMENT,
   CREATED_BY                   VARCHAR(20)  NOT NULL,
   CREATED_DATE                 TIMESTAMP    NOT NULL,
   UPDATED_BY                   VARCHAR(20)  NOT NULL,
   UPDATED_DATE                 TIMESTAMP    NOT NULL,
   DEPARTMENT_CODE              VARCHAR(10)  NOT NULL,
   DEPARTMENT_NAME              VARCHAR(50)  NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE KEY (DEPARTMENT_CODE),
   KEY (UPDATED_DATE)
);
```

### data.sql

```sql
insert into DEPARTMENT_INFO (CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, DEPARTMENT_CODE, DEPARTMENT_NAME)
values
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '1', '总公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '101', '北京分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '102', '上海分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '103', '广州分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '104', '深圳分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '105', '重庆分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '106', '四川分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '107', '山西分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '108', '河北分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '109', '河南分公司'),
    ('SYSTEM', CURRENT_TIMESTAMP(), 'SYSTEM', CURRENT_TIMESTAMP(), '110', '陕西分公司');
```

## 单元测试

### DepartmentDTOTest

```java
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
```

### DepartmentServiceTest

```java
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
```
