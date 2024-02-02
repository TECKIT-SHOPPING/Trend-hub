package com.trendhub.trendhub.domain.banner.service;

import com.trendhub.trendhub.domain.banner.entity.Banner;
import com.trendhub.trendhub.domain.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }
}
