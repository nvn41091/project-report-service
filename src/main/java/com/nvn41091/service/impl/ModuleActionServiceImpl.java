package com.nvn41091.service.impl;

import com.nvn41091.service.ModuleActionService;
import com.nvn41091.domain.ModuleAction;
import com.nvn41091.repository.ModuleActionRepository;
import com.nvn41091.service.dto.ModuleActionDTO;
import com.nvn41091.service.mapper.ModuleActionMapper;
import com.nvn41091.utils.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<ModuleActionDTO> getAllByModuleId(Long id) {
        return moduleActionRepository.getAllByModuleId(id).stream()
                .map(moduleActionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateByModule(String actionId, Long moduleId) {
        List<String> lstAction = new ArrayList<>();
        if (StringUtils.isNoneEmpty(actionId)) {
            lstAction = Arrays.asList(actionId.split(",").clone());
        }
        List<ModuleAction> selected = new ArrayList<>();
        for (String action : lstAction) {
            ModuleAction moduleAction = new ModuleAction();
            moduleAction.setActionId(DataUtil.safeToLong(action));
            moduleAction.setModuleId(moduleId);
            moduleAction.setUpdateTime(Instant.now());
            selected.add(moduleAction);
        }
        List<ModuleAction> origin = moduleActionRepository.getAllByModuleId(moduleId);
        Iterator<ModuleAction> i = origin.listIterator();
        while (i.hasNext()) {
            ModuleAction nextOrigin = i.next();
            boolean isUncheck = true;
            Iterator<ModuleAction> j = selected.listIterator();
            while (j.hasNext()) {
                ModuleAction nextSelected = j.next();
                if (nextSelected.getModuleId().equals(nextOrigin.getModuleId()) && nextSelected.getActionId().equals(nextOrigin.getActionId())) {
                    j.remove();
                    isUncheck = false;
                }
            }
            if (!isUncheck) {
                i.remove();
            }
        }
        moduleActionRepository.deleteInBatch(origin);
        moduleActionRepository.saveAll(selected);
    }

    @Override
    public void deleteByModuleId(Long id) {
        moduleActionRepository.deleteAllByModuleId(id);
    }
}
