package org.noah.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
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
}
