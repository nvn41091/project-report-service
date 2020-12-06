package com.nvn41091.service.impl;

import com.nvn41091.service.AppParamService;
import com.nvn41091.domain.AppParam;
import com.nvn41091.repository.AppParamRepository;
import com.nvn41091.service.dto.AppParamDTO;
import com.nvn41091.service.mapper.AppParamMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (appParamDTO.getId() != null && appParamRepository.findAllById(appParamDTO.getId()).isEmpty()) {
            throw new BadRequestAlertException(Translator.toLocale("error.appParam.notExist"), "appParam", "appParam.notExist");
        }
        appParamDTO.setUpdateTime(Instant.now());
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
    public Page<AppParamDTO> doSearch(AppParamDTO appParamDTO, Pageable pageable) {
        log.debug("Request to search all AppParams");
        return appParamRepository.doSearch(DataUtil.makeLikeParam(appParamDTO.getName()),
                DataUtil.makeLikeParam(appParamDTO.getType()),
                appParamDTO.isStatus(), pageable)
                .map(appParamMapper::toDto);
    }

    @Override
    public List<String> autoCompleteType(String type) {
        Pageable req = PageRequest.of(0, 10);
        return appParamRepository.autoCompleteType(DataUtil.makeLikeParam(type), req);
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

    @Override
    public List<AppParamDTO> getValueByType(AppParamDTO appParamDTO) {
        return appParamRepository.getAllByTypeAndStatusOrderByTypeAscOrdAsc(appParamDTO.getType(), Boolean.TRUE)
                .stream().map(appParamMapper::toDto).collect(Collectors.toList());
    }
}
