package com.trendhub.trendhub.domain.likes.service;

import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.likes.repository.LikesRepository;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    public Optional<Likes> findByProductAndUser(Product product, User user) {
        return likesRepository.findByProductAndUser(product, user);
    }

    public void createLikes(Likes likes) {
        likesRepository.save(likes);
    }

    public void deleteLikes(Likes likes) {
        likesRepository.delete(likes);
    }
}
