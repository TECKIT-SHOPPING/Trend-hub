package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.product.entity.SubCategory;
import com.trendhub.trendhub.domain.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategory findById(Long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new IllegalStateException("존재하지않는 서브카테고리입니다."));
        return subCategory;
    }

}
