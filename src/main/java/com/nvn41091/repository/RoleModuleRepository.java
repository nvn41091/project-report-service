package com.nvn41091.repository;

import com.nvn41091.domain.RoleModule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RoleModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {
}
