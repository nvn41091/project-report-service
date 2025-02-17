package com.nvn41091.repository;

import com.nvn41091.domain.CompanyUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CompanyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    CompanyUser getCompanyUserByUserIdAndCompanyId(Long userId, Long companyId);

    void deleteByUserId(Long userId);

    void deleteByCompanyId(Long companyId);

    void deleteByUserIdAndCompanyId(Long userId, Long companyId);

    List<CompanyUser> findAllByCompanyId(Long companyId);
}
