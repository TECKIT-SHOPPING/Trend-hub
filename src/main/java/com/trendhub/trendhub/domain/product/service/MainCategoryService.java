package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.product.entity.MainCategory;
import com.trendhub.trendhub.domain.product.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public MainCategory findById(Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).orElseThrow(() -> new IllegalStateException("존재하지않는 메인카테고리입니다."));
        return mainCategory;
    }
}
