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
