package com.nvn41091.repository;

import com.nvn41091.domain.AppParam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AppParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppParamRepository extends JpaRepository<AppParam, Long> {
}
