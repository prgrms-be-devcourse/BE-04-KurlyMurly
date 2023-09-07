package com.devcourse.kurlymurly.web.dto;

import java.util.List;

public class ListPagingResponse<T> {
    private final List<T> items;
    private final int amount;

    public ListPagingResponse(List<T> items) {
        this.items = items;
        this.amount = items.size();
    }
}
