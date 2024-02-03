package com.trendhub.trendhub.domain.coordi.repository;

import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordiRepository extends JpaRepository<Coordi, Long>, CoordiRepositoryCustom {
}
