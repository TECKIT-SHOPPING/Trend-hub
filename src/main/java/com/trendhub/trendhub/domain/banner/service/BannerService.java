package com.trendhub.trendhub.domain.banner.service;

import com.trendhub.trendhub.domain.banner.entity.Banner;
import com.trendhub.trendhub.domain.banner.repository.BannerRepository;
import com.trendhub.trendhub.domain.product.entity.Product;
import com.trendhub.trendhub.domain.product.service.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    public Banner getBanner(Long id) {
        Optional<Banner> banner = this.bannerRepository.findById(id);
        if (banner.isPresent()) {
            return banner.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }
}
