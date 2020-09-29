package com.nvn41091.service.impl;

import com.nvn41091.service.ActionService;
import com.nvn41091.domain.Action;
import com.nvn41091.repository.ActionRepository;
import com.nvn41091.service.dto.ActionDTO;
import com.nvn41091.service.mapper.ActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Action}.
 */
@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private final Logger log = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public ActionDTO save(ActionDTO actionDTO) {
        log.debug("Request to save Action : {}", actionDTO);
        Action action = actionMapper.toEntity(actionDTO);
        action = actionRepository.save(action);
        return actionMapper.toDto(action);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActionDTO> doSearch(ActionDTO actionDTO, Pageable pageable) {
        log.debug("Request to find Actions");
        return actionRepository.findAll(pageable)
            .map(actionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Action : {}", id);
        actionRepository.deleteById(id);
    }
}
