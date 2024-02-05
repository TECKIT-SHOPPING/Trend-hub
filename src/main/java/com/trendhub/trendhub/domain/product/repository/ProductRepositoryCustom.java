package com.trendhub.trendhub.domain.product.repository;

import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.user.entity.User;

import java.util.List;

public interface ProductRepositoryCustom{

    List<ProductDto> findTop10ByOrderByViewCountDesc(User user);

    List<ProductDto> findTop10ByOrderByViewCountDescAnonymousUser();

    List<ProductDto> findByLikesProducts(User user);
}
