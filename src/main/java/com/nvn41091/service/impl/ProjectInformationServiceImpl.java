package com.nvn41091.service.impl;

import com.nvn41091.security.SecurityUtils;
import com.nvn41091.service.ProjectInformationService;
import com.nvn41091.domain.ProjectInformation;
import com.nvn41091.repository.ProjectInformationRepository;
import com.nvn41091.service.dto.ProjectInformationDTO;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.mapper.ProjectInformationMapper;
import com.nvn41091.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectInformation}.
 */
@Service
@Transactional
public class ProjectInformationServiceImpl implements ProjectInformationService {

    private final Logger log = LoggerFactory.getLogger(ProjectInformationServiceImpl.class);

    private final ProjectInformationRepository projectInformationRepository;

    private final ProjectInformationMapper projectInformationMapper;

    public ProjectInformationServiceImpl(ProjectInformationRepository projectInformationRepository, ProjectInformationMapper projectInformationMapper) {
        this.projectInformationRepository = projectInformationRepository;
        this.projectInformationMapper = projectInformationMapper;
    }

    @Override
    public ProjectInformationDTO save(ProjectInformationDTO projectInformationDTO) {
        log.debug("Request to save ProjectInformation : {}", projectInformationDTO);
        ProjectInformation projectInformation = projectInformationMapper.toEntity(projectInformationDTO);
        projectInformation = projectInformationRepository.save(projectInformation);
        return projectInformationMapper.toDto(projectInformation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectInformations");
        return projectInformationRepository.findAll(pageable)
            .map(projectInformationMapper::toDto);
    }

    @Override
    public Page<ProjectInformationDTO> doSearch(ProjectInformationDTO projectInformationDTO, Pageable pageable) {
        UserDTO current = SecurityUtils.getCurrentUser().get();
        Instant endDatePlanStart = null;
        Instant endDatePlanEnd = null;
        if (projectInformationDTO.getEndPlan() != null) {
            endDatePlanStart = projectInformationDTO.getEndPlan().toInstant().minus(7, ChronoUnit.DAYS);
            endDatePlanEnd =projectInformationDTO.getEndPlan().toInstant().plus(7, ChronoUnit.DAYS);
        }
        return projectInformationRepository.doSearch(DataUtil.makeLikeParam(projectInformationDTO.getName()),
                DataUtil.makeLikeParam(projectInformationDTO.getCode()),
                projectInformationDTO.getStatus(),
                current.getCompanyId(),
                DataUtil.toInstant(projectInformationDTO.getStart()),
                DataUtil.toInstant(projectInformationDTO.getEndTime()),
                DataUtil.toInstant(projectInformationDTO.getEndPlan()),
                endDatePlanStart,
                endDatePlanEnd,
                pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectInformationDTO> findOne(Long id) {
        log.debug("Request to get ProjectInformation : {}", id);
        return projectInformationRepository.findById(id)
            .map(projectInformationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectInformation : {}", id);
        projectInformationRepository.deleteById(id);
    }
}
