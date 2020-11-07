package com.nvn41091.service.impl;

import com.nvn41091.security.SecurityUtils;
import com.nvn41091.service.CompanyService;
import com.nvn41091.domain.Company;
import com.nvn41091.repository.CompanyRepository;
import com.nvn41091.service.CompanyUserService;
import com.nvn41091.service.UserRoleService;
import com.nvn41091.service.dto.CompanyDTO;
import com.nvn41091.service.dto.CompanyUserDTO;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.dto.UserRoleDTO;
import com.nvn41091.service.mapper.CompanyMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.time.Instant;
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

    private final CompanyUserService companyUserService;

    private final UserRoleService userRoleService;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, CompanyUserService companyUserService, UserRoleService userRoleService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.companyUserService = companyUserService;
        this.userRoleService = userRoleService;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            // Validate truong hop them moi
        } else {
            // Validate truong hop update
            if (companyRepository.findAllById(companyDTO.getId()).size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.company.notExist"), "company", "company.notExist");
            }
        }
        // Validate chung
        if (companyRepository.findAllByCodeAndIdNotEqual(companyDTO.getCode(), companyDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.company.codeExist"), "company", "company.notExist");
        }
        if (companyRepository.findAllByEmailAndIdNotEqual(companyDTO.getEmail(), companyDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.company.emailExist"), "company", "company.notExist");
        }
        companyDTO.setUpdateTime(Instant.now());
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        if (companyDTO.getId() == null) {
            UserDTO userDTO = SecurityUtils.getCurrentUser().get();
            CompanyUserDTO companyUserDTO = new CompanyUserDTO(null, userDTO.getId(), company.getId(), Boolean.TRUE, Instant.now());
            UserRoleDTO userRoleDTO = new UserRoleDTO(null, userDTO.getId(), 3L, company.getId(), Instant.now());
            userRoleService.save(userRoleDTO);
            companyUserService.save(companyUserDTO);
        }
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
    public Page<CompanyDTO> doSearch(CompanyDTO companyDTO, Pageable pageable) {
        log.debug("Request to search Companies");
        return companyRepository.doSearch(DataUtil.makeLikeParam(companyDTO.getCode()),
                DataUtil.makeLikeParam(companyDTO.getName()),
                DataUtil.makeLikeParam(companyDTO.getEmail()),
                DataUtil.makeLikeParam(companyDTO.getTel()),
                companyDTO.isStatus(),
                pageable).map(companyMapper::toDto);
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
        if (companyRepository.findAllById(id).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.company.notExist"), "company", "company.notExist");
        }
        companyRepository.deleteById(id);
        companyUserService.deleteByCompanyId(id);
        userRoleService.deleteByCompanyId(id);
    }
}
