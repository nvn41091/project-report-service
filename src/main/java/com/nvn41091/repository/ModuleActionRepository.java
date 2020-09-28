package com.nvn41091.repository;

import com.nvn41091.domain.ModuleAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ModuleAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleActionRepository extends JpaRepository<ModuleAction, Long> {
}
