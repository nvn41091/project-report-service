package com.nvn41091.service.impl;

import com.nvn41091.service.AppParamService;
import com.nvn41091.domain.AppParam;
import com.nvn41091.repository.AppParamRepository;
import com.nvn41091.service.dto.AppParamDTO;
import com.nvn41091.service.mapper.AppParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AppParam}.
 */
@Service
@Transactional
public class AppParamServiceImpl implements AppParamService {

    private final Logger log = LoggerFactory.getLogger(AppParamServiceImpl.class);

    private final AppParamRepository appParamRepository;

    private final AppParamMapper appParamMapper;

    public AppParamServiceImpl(AppParamRepository appParamRepository, AppParamMapper appParamMapper) {
        this.appParamRepository = appParamRepository;
        this.appParamMapper = appParamMapper;
    }

    @Override
    public AppParamDTO save(AppParamDTO appParamDTO) {
        log.debug("Request to save AppParam : {}", appParamDTO);
        AppParam appParam = appParamMapper.toEntity(appParamDTO);
        appParam = appParamRepository.save(appParam);
        return appParamMapper.toDto(appParam);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppParams");
        return appParamRepository.findAll(pageable)
            .map(appParamMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AppParamDTO> findOne(Long id) {
        log.debug("Request to get AppParam : {}", id);
        return appParamRepository.findById(id)
            .map(appParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppParam : {}", id);
        appParamRepository.deleteById(id);
    }
}
