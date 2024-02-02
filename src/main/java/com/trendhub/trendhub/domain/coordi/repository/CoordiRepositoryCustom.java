package com.trendhub.trendhub.domain.coordi.repository;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.user.entity.User;

import java.util.List;

public interface CoordiRepositoryCustom {

    List<CoordiDto> findTop5ByOrderByViewCountDesc(User user);
}
