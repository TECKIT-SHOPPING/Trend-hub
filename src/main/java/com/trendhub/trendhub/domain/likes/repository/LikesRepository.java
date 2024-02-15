package com.trendhub.trendhub.domain.likes.repository;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByProductAndUser(Product product, User user);

    Optional<Likes> findByCoordiAndUser(Coordi coordi, User user);
}
