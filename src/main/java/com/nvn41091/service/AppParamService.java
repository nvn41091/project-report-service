package com.nvn41091.service;

import com.nvn41091.service.dto.AppParamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nvn41091.domain.AppParam}.
 */
public interface AppParamService {

    /**
     * Save a appParam.
     *
     * @param appParamDTO the entity to save.
     * @return the persisted entity.
     */
    AppParamDTO save(AppParamDTO appParamDTO);

    /**
     * Get all the appParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppParamDTO> findAll(Pageable pageable);

    Page<AppParamDTO> doSearch(AppParamDTO appParamDTO, Pageable pageable);

    List<String> autoCompleteType(String type);

    /**
     * Get the "id" appParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppParamDTO> findOne(Long id);

    /**
     * Delete the "id" appParam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<AppParamDTO> getValueByType(AppParamDTO appParamDTO);
}
