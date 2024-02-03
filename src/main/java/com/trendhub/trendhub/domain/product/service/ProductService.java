package com.trendhub.trendhub.domain.product.service;

import com.nimbusds.oauth2.sdk.GeneralException;
import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.likes.service.LikesService;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
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
    private final LikesService likesService;
    private final UserRepository userRepository;

    public List<ProductDto> findTop10ViewCountDesc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != "anonymousUser") {
            User user = null;
            String loginId = authentication.getName();
            //세션을 통한 유저 조회
            user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
            List<ProductDto> result = productRepository.findTop10ByOrderByViewCountDesc(user);
            return result;
        } else {
            //비로그인상태
            List<ProductDto> result = productRepository.findTop10ByOrderByViewCountDescAnonymousUser();
            return result;
        }
    }

    public boolean toggleLikeProduct(ProductLikeDto productLikeDto) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findById(productLikeDto.getProductId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 상품입니다."));
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        Optional<Likes> _findLikes = likesService.findByProductAndUser(product, user);

        if (_findLikes.isEmpty()) {
            //좋아요를 누른적 없다면 likes 생성후, 좋아요 처리
            Likes likes = productLikeDto.toEntity(user, product);
            likesService.createLikes(likes);
            product.likeProduct(likes);
            return true;
        } else {
            //좋아요 누른적 있다면 취소 처리 후 데이터 삭제
            Likes findLikes = _findLikes.get();
            product.unLikeProduct(findLikes);
            likesService.deleteLikes(findLikes);
            return false;
        }
    }
}
