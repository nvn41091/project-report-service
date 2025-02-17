package com.nvn41091.service;

import com.nvn41091.service.dto.ActionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing {@link com.nvn41091.domain.Action}.
 */
public interface ActionService {

    /**
     * Save a action.
     *
     * @param actionDTO the entity to save.
     * @return the persisted entity.
     */
    ActionDTO save(ActionDTO actionDTO);

    /**
     * Get all the actions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActionDTO> doSearch(ActionDTO actionDTO, Pageable pageable);

    List<ActionDTO> getAll();

    /**
     * Delete the "id" action.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
