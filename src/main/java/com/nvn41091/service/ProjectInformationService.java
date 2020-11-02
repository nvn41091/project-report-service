package com.nvn41091.service;

import com.nvn41091.service.dto.ProjectInformationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.ProjectInformation}.
 */
public interface ProjectInformationService {

    /**
     * Save a projectInformation.
     *
     * @param projectInformationDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectInformationDTO save(ProjectInformationDTO projectInformationDTO);

    /**
     * Get all the projectInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectInformationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" projectInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectInformationDTO> findOne(Long id);

    /**
     * Delete the "id" projectInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
