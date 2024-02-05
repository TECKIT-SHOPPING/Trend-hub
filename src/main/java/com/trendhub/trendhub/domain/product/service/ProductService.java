package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.repository.ProductRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductDto> findTop10ViewCountDesc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            String loginId = authentication.getName();
            //세션을 통한 유저 조회
            user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        }
        List<ProductDto> result = productRepository.findTop10ByOrderByViewCountDesc(user);
        return result;
    }

    public Product getPost(Integer id) {
        Optional<Product> product = this.productRepository.findById(Long.valueOf(id));
        if(product.isPresent()) {
            return product.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }
}
