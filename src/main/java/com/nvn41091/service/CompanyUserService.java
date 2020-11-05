package com.nvn41091.service;

import com.nvn41091.service.dto.CompanyUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.CompanyUser}.
 */
public interface CompanyUserService {

    /**
     * Save a companyUser.
     *
     * @param companyUserDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyUserDTO save(CompanyUserDTO companyUserDTO);

    /**
     * Get all the companyUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyUserDTO> findOne(Long id);

    /**
     * Delete the "id" companyUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void deleteByCompanyId(Long companyId);
}
