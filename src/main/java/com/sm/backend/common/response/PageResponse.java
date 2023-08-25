package com.sm.backend.common.response;

import java.util.List;

public class PageResponse<T> {

    private Integer size;

    private Integer total;

    private String totalCount;

    private boolean hasNext;

    private List<T> items;


    public void setPage(Integer size, Integer total, boolean hasNext) {
        this.size = size;
        this.total = total;
        this.hasNext = hasNext;
    }

    public void setPage(Integer total) {
        this.total = total;
    }
    public void setPage(String totalCount) {
        this.totalCount = totalCount;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
