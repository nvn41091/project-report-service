package com.nvn41091.service;

import com.nvn41091.service.dto.CompanyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.Company}.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyDTO> findAll(Pageable pageable);

    Page<CompanyDTO> doSearch(CompanyDTO companyDTO, Pageable pageable);


    /**
     * Get the "id" company.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyDTO> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
