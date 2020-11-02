package com.nvn41091.service.impl;

import com.nvn41091.service.ProjectInformationService;
import com.nvn41091.domain.ProjectInformation;
import com.nvn41091.repository.ProjectInformationRepository;
import com.nvn41091.service.dto.ProjectInformationDTO;
import com.nvn41091.service.mapper.ProjectInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
