package com.nvn41091.service;

import com.nvn41091.service.dto.ModuleActionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.ModuleAction}.
 */
public interface ModuleActionService {

    /**
     * Save a moduleAction.
     *
     * @param moduleActionDTO the entity to save.
     * @return the persisted entity.
     */
    ModuleActionDTO save(ModuleActionDTO moduleActionDTO);

    /**
     * Get all the moduleActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModuleActionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" moduleAction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModuleActionDTO> findOne(Long id);

    /**
     * Delete the "id" moduleAction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
