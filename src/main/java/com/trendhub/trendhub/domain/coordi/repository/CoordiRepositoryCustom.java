package com.trendhub.trendhub.domain.coordi.repository;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDetailDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoordiRepositoryCustom {

    List<CoordiDto> findTop5ByOrderByViewCountDesc(User user);

    List<CoordiDto> findTop5ByOrderByViewCountDescAnonymousUser();
    Page<CoordiDto> coordiPage(User user, Pageable pageable);

    CoordiDetailDto findCoordiById(User user, Long Id);


}
