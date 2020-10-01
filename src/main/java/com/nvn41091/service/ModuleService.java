package com.nvn41091.service;

import com.nvn41091.service.dto.ModuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.Module}.
 */
public interface ModuleService {

    /**
     * Save a module.
     *
     * @param moduleDTO the entity to save.
     * @return the persisted entity.
     */
    ModuleDTO save(ModuleDTO moduleDTO);

    /**
     * Get all the modules.
     *
     * @return the list of entities.
     */
    List<ModuleDTO> doSearch(ModuleDTO moduleDTO);


    /**
     * Get the "id" module.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModuleDTO> findOne(Long id);

    /**
     * Delete the "id" module.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ModuleDTO> findAllParent();
}
