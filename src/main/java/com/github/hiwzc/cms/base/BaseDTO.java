package com.github.hiwzc.cms.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseDTO implements Serializable {
    private String id;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
