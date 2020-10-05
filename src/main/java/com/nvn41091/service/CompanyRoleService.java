package com.nvn41091.service;

import com.nvn41091.service.dto.CompanyRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.CompanyRole}.
 */
public interface CompanyRoleService {

    /**
     * Save a companyRole.
     *
     * @param companyRoleDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyRoleDTO save(CompanyRoleDTO companyRoleDTO);

    /**
     * Get all the companyRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyRoleDTO> findOne(Long id);

    /**
     * Delete the "id" companyRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CompanyRoleDTO> getAllByCompanyId(Long id);

    List<CompanyRoleDTO> saveAll(List<CompanyRoleDTO> lst, Long companyId);
}
