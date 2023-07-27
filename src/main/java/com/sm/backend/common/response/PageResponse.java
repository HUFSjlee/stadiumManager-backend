package com.sm.backend.common.response;

import java.util.List;

public class PageResponse<T> {

    private Integer size; // 페이지 크기

    private Integer total; // 전체 요소 수

    private String totalCount; // 행안부 전체 요소 수

    private boolean hasNext; // 다음 페이지 여부

    private List<T> items;

    // ...

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
