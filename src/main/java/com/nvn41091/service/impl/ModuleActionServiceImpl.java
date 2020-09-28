package com.nvn41091.service.impl;

import com.nvn41091.service.ModuleActionService;
import com.nvn41091.domain.ModuleAction;
import com.nvn41091.repository.ModuleActionRepository;
import com.nvn41091.service.dto.ModuleActionDTO;
import com.nvn41091.service.mapper.ModuleActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModuleAction}.
 */
@Service
@Transactional
public class ModuleActionServiceImpl implements ModuleActionService {

    private final Logger log = LoggerFactory.getLogger(ModuleActionServiceImpl.class);

    private final ModuleActionRepository moduleActionRepository;

    private final ModuleActionMapper moduleActionMapper;

    public ModuleActionServiceImpl(ModuleActionRepository moduleActionRepository, ModuleActionMapper moduleActionMapper) {
        this.moduleActionRepository = moduleActionRepository;
        this.moduleActionMapper = moduleActionMapper;
    }

    @Override
    public ModuleActionDTO save(ModuleActionDTO moduleActionDTO) {
        log.debug("Request to save ModuleAction : {}", moduleActionDTO);
        ModuleAction moduleAction = moduleActionMapper.toEntity(moduleActionDTO);
        moduleAction = moduleActionRepository.save(moduleAction);
        return moduleActionMapper.toDto(moduleAction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuleActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleActions");
        return moduleActionRepository.findAll(pageable)
            .map(moduleActionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleActionDTO> findOne(Long id) {
        log.debug("Request to get ModuleAction : {}", id);
        return moduleActionRepository.findById(id)
            .map(moduleActionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModuleAction : {}", id);
        moduleActionRepository.deleteById(id);
    }
}
