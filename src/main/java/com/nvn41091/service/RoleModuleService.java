package com.nvn41091.service;

import com.nvn41091.service.dto.RoleModuleDTO;

import com.nvn41091.service.dto.TreeViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.RoleModule}.
 */
public interface RoleModuleService {

    /**
     * Save a roleModule.
     *
     * @param roleModuleDTO the entity to save.
     * @return the persisted entity.
     */
    RoleModuleDTO save(RoleModuleDTO roleModuleDTO);

    /**
     * Get all the roleModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RoleModuleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" roleModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleModuleDTO> findOne(Long id);

    /**
     * Delete the "id" roleModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<TreeViewDTO> getALl(Long id);
}
