package com.redscooter.API.common.responseFactory;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T extends Object> {
    int currentPage;
    Long totalItems;
    int totalPages;
    List<T> items;

    public PageResponse(Page<T> page) {
        this(page, page.getContent());
    }

    public PageResponse(int currentPage, Long totalItems, int totalPages, List<T> items) {
        setCurrentPage(currentPage);
        setTotalItems(totalItems);
        setTotalPages(totalPages);
        setItems(items);
    }

    public PageResponse(Page<T> page, List<T> items) {
        this(page.getNumber(), page.getTotalElements(), page.getTotalPages(), items);
    }
}
