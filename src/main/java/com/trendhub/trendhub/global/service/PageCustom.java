package com.trendhub.trendhub.global.service;

import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;


@Getter
public class PageCustom<T> implements Serializable {
    private List<T> content;

    private PageableCustom pageableCustom;

    public PageCustom(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.pageableCustom = new PageableCustom(new PageImpl(content, pageable, total));
    }

}
