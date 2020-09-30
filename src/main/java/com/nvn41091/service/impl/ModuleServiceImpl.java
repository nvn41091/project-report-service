package com.nvn41091.service.impl;

import com.nvn41091.service.ModuleService;
import com.nvn41091.domain.Module;
import com.nvn41091.repository.ModuleRepository;
import com.nvn41091.service.dto.ModuleDTO;
import com.nvn41091.service.mapper.ModuleMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Module}.
 */
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleServiceImpl(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public ModuleDTO save(ModuleDTO moduleDTO) {
        log.debug("Request to save Module : {}", moduleDTO);
        if (moduleDTO.getId() == null) {
            // Validate them moi
        } else {
            // Validate cap nhat
            if (moduleRepository.findAllById(moduleDTO.getId()).size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.module.notExist"), "module", "module.notExist");
            }
        }
        if (moduleRepository.findAllByCodeAndIdNotContains(moduleDTO.getCode(), moduleDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.module.codeExist"), "module", "module.codeExist");
        }
        moduleDTO.setUpdateTime(Instant.now());
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        return moduleMapper.toDto(module);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuleDTO> doSearch(ModuleDTO moduleDTO, Pageable pageable) {
        log.debug("Request to get all Modules");
        return moduleRepository.doSearch(DataUtil.makeLikeParam(moduleDTO.getCode()), DataUtil.makeLikeParam(moduleDTO.getName()), moduleDTO.isStatus(), moduleDTO.getParentId(), pageable)
                .map(moduleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleDTO> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id)
                .map(moduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        if (moduleRepository.findAllById(id).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.module.notExist"), "module", "module.notExist");
        }
        moduleRepository.deleteById(id);
    }

    @Override
    public List<ModuleDTO> findAllParent() {
        log.debug("Request to get all parent");
        return moduleRepository.findAllParent().stream()
                .map(moduleMapper::toDto).collect(Collectors.toList());
    }
}
