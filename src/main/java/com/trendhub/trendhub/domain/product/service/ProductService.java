package com.trendhub.trendhub.domain.product.service;

import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.likes.service.LikesService;
import com.trendhub.trendhub.domain.product.dto.ProductDto;
import com.trendhub.trendhub.domain.product.dto.ProductLikeDto;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.repository.ProductRepository;
import com.trendhub.trendhub.domain.product.repository.QnaRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Product getProduct(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public List<ProductDto> likesProductList(User user) {
        List<ProductDto> result = productRepository.findByLikesProducts(user);
        return result;
    }

    public List<ProductDto> getProductsByIds(List<Long> productIdList) {

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<ProductDto> result = productRepository.findByRecentlyProductsIn(user, productIdList);
        return result;
    }

    public Page<ProductDto> categoryProductList(Long mainCategory, Long subCategory, int page, String sort) {
        Pageable pageable;

        // 기본 정렬: 인기순
        Sort defaultSort = Sort.by("popular").descending();

        // 낮은 가격순
        if ("low-price".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("low-price").ascending());
        }
        // 높은 가격순
        else if ("high-price".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("high-price").descending());
        }
        // 할인율순
        else if ("discount".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("discount").descending());
        }
        // 기본 정렬: 인기순
        else {
            pageable = PageRequest.of(page - 1, 20, defaultSort);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            user = userRepository.findByLoginId(authentication.getName()).get();
        }
        Page<ProductDto> result = productRepository.categoryProductList(mainCategory, subCategory, user, pageable);

        return result;
    }

    public void createQna(QnaDto qnaDto, Product product, User user) {
        QnA saveQnA = qnaDto.toEntity(product, user);
        this.qnaRepository.save(saveQnA);
    }


    public Page<ProductDto> searchProductList(String q, int page, String sort) {
        String keyword = q.replaceAll("\\s+", " ")
                .trim()
                .replaceAll("\\s+", "%");


        Pageable pageable;

        // 기본 정렬: 인기순
        Sort defaultSort = Sort.by("popular").descending();

        // 낮은 가격순
        if ("low-price".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("low-price").ascending());
        }
        // 높은 가격순
        else if ("high-price".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("high-price").descending());
        }
        // 할인율순
        else if ("discount".equals(sort)) {
            pageable = PageRequest.of(page - 1, 20, Sort.by("discount").descending());
        }
        // 기본 정렬: 인기순
        else {
            pageable = PageRequest.of(page - 1, 20, defaultSort);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            user = userRepository.findByLoginId(authentication.getName()).get();
        }
        Page<ProductDto> result = productRepository.searchProductList(keyword, user, pageable);

        return result;
    }
}