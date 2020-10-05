package com.nvn41091.repository;

import com.nvn41091.domain.CompanyRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CompanyRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRoleRepository extends JpaRepository<CompanyRole, Long> {

    List<CompanyRole> getAllByCompanyId(Long id);

}
