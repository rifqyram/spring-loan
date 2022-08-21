package com.enigma.loan_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private long count;
    private int totalPage;
    private int page;
    private int size;
    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.count = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
    }
}
