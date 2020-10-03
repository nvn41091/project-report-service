package com.nvn41091.service.impl;

import com.nvn41091.service.ModuleActionService;
import com.nvn41091.service.ModuleService;
import com.nvn41091.domain.Module;
import com.nvn41091.repository.ModuleRepository;
import com.nvn41091.service.dto.ModuleDTO;
import com.nvn41091.service.mapper.ModuleMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
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

    private final ModuleActionService moduleActionService;

    private final ModuleMapper moduleMapper;

    public ModuleServiceImpl(ModuleRepository moduleRepository, ModuleMapper moduleMapper, ModuleActionService moduleActionService) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
        this.moduleActionService = moduleActionService;
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
            if (StringUtils.isNoneEmpty(moduleDTO.getPathUrl())) {
                if (moduleRepository.findAllByParentId(moduleDTO.getId()).size() > 0) {
                    throw new BadRequestAlertException(Translator.toLocale("error.module.changeToGroup"), "module", "module.changeToGroup");
                }
            } else {
                if (moduleDTO.getStatus().equals(Boolean.FALSE) && moduleRepository.findAllByParentIdAndStatus(moduleDTO.getId(), Boolean.TRUE).size() > 0) {
                    throw new BadRequestAlertException(Translator.toLocale("error.module.dontLockMenu"), "module", "module.dontLockMenu");
                }
            }
        }
        if (moduleDTO.getParentId() != null && moduleDTO.getStatus().equals(Boolean.TRUE)) {
            if (moduleRepository.findAllByIdAndStatus(moduleDTO.getParentId(), Boolean.FALSE).size() > 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.module.parentLock"), "module", "module.parentLock");
            }
        }
        if (moduleRepository.findAllByCodeAndIdNotContains(moduleDTO.getCode(), moduleDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.module.codeExist"), "module", "module.codeExist");
        }
        moduleDTO.setUpdateTime(Instant.now());
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        this.moduleActionService.updateByModule(moduleDTO.getActionId(), module.getId());
        return moduleMapper.toDto(module);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleDTO> doSearch(ModuleDTO moduleDTO) {
        log.debug("Request to get all Modules");
        return moduleRepository.doSearch(DataUtil.makeLikeParam(moduleDTO.getCode()), DataUtil.makeLikeParam(moduleDTO.getName()), moduleDTO.isStatus(), moduleDTO.getParentId())
                .stream().map(moduleMapper::toDto).collect(Collectors.toList());
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
        if (moduleRepository.findAllByParentId(id).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.module.parentExist"), "module", "module.parentExist");
        }
        moduleRepository.deleteById(id);
        moduleActionService.deleteByModuleId(id);
    }

    @Override
    public List<ModuleDTO> findAllParent() {
        log.debug("Request to get all parent");
        return moduleRepository.findAllParent().stream()
                .map(moduleMapper::toDto).collect(Collectors.toList());
    }
}
