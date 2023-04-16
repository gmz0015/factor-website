package org.noah.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

public class PageEntity {
    private List<?> records;

    private Long limit;

    private Long page;

    private Long totalPage;

    private Long totalCount;

    private String sort;

    public static PageEntity convertFromIPage(IPage<?> iPage) {
        PageEntity page = new PageEntity();
        page.setRecords(iPage.getRecords());
        page.setPage(iPage.getCurrent());
        page.setLimit(iPage.getSize());
        page.setTotalCount(iPage.getTotal());
        page.setTotalPage(iPage.getPages());
        return page;
    }

    public List<?> getRecords() {
        return records;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
