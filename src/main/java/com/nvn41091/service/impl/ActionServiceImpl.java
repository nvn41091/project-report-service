package com.nvn41091.service.impl;

import com.nvn41091.configuration.Constants;
import com.nvn41091.repository.ModuleActionRepository;
import com.nvn41091.service.ActionService;
import com.nvn41091.domain.Action;
import com.nvn41091.repository.ActionRepository;
import com.nvn41091.service.dto.ActionDTO;
import com.nvn41091.service.mapper.ActionMapper;
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
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Action}.
 */
@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private final Logger log = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    private final ModuleActionRepository moduleActionRepository;

    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper, ModuleActionRepository moduleActionRepository) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
        this.moduleActionRepository = moduleActionRepository;
    }

    @Override
    public ActionDTO save(ActionDTO actionDTO) {
        log.debug("Request to save Action : {}", actionDTO);
        if (actionDTO.getId() == null) {
            // valdate truong them moi
        } else {
            // Validate truong cap nhat
            if (actionRepository.findAllById(actionDTO.getId()).isEmpty()) {
                throw new BadRequestAlertException(Translator.toLocale("error.action.notExist"), Constants.ACTION, "action.notExist");
            }
        }
        if (!actionRepository.findAllByCodeAndIdNotEqual(actionDTO.getCode(), actionDTO.getId()).isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("error.action.codeExist"), Constants.ACTION, "action.exits");
        }
        actionDTO.setUpdateTime(Instant.now());
        Action action = actionMapper.toEntity(actionDTO);
        action = actionRepository.save(action);
        return actionMapper.toDto(action);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActionDTO> doSearch(ActionDTO actionDTO, Pageable pageable) {
        log.debug("Request to find Actions");
        return actionRepository.doSearch(DataUtil.makeLikeParam(actionDTO.getCode()),
                DataUtil.makeLikeParam(actionDTO.getName()),
                actionDTO.isStatus(), pageable)
                .map(actionMapper::toDto);
    }

    @Override
    public List<ActionDTO> getAll() {
        return actionRepository.getAllByStatus(true).stream().map(actionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Action : {}", id);
        if (actionRepository.findAllById(id).isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("error.action.notExist"), Constants.ACTION, "action.notExist");
        }
        if (!moduleActionRepository.findAllByActionId(id).isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("error.action.using"), Constants.ACTION, "action.using");
        }
        actionRepository.deleteById(id);
    }
}
