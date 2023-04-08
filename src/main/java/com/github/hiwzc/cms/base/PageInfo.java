package com.github.hiwzc.cms.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageInfo<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;
    private List<T> list;
}
