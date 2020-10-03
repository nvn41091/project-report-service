package com.nvn41091.repository;

import com.nvn41091.domain.UserRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> getAllByUserId(Long id);
}
