package com.nvn41091.repository;

import com.nvn41091.domain.CompanyUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    CompanyUser getCompanyUserByUserId(Long userId);

    void deleteByUserId(Long userId);

    void deleteByCompanyId(Long companyId);
}
