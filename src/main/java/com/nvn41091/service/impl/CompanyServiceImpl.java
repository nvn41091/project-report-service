package com.nvn41091.service.impl;

import com.nvn41091.service.CompanyService;
import com.nvn41091.domain.Company;
import com.nvn41091.repository.CompanyRepository;
import com.nvn41091.service.dto.CompanyDTO;
import com.nvn41091.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable)
            .map(companyMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyDTO> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id)
            .map(companyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
