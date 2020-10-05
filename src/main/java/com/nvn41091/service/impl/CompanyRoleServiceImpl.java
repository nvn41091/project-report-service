package com.nvn41091.service.impl;

import com.nvn41091.domain.RoleModule;
import com.nvn41091.service.CompanyRoleService;
import com.nvn41091.domain.CompanyRole;
import com.nvn41091.repository.CompanyRoleRepository;
import com.nvn41091.service.dto.CompanyRoleDTO;
import com.nvn41091.service.mapper.CompanyRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompanyRole}.
 */
@Service
@Transactional
public class CompanyRoleServiceImpl implements CompanyRoleService {

    private final Logger log = LoggerFactory.getLogger(CompanyRoleServiceImpl.class);

    private final CompanyRoleRepository companyRoleRepository;

    private final CompanyRoleMapper companyRoleMapper;

    public CompanyRoleServiceImpl(CompanyRoleRepository companyRoleRepository, CompanyRoleMapper companyRoleMapper) {
        this.companyRoleRepository = companyRoleRepository;
        this.companyRoleMapper = companyRoleMapper;
    }

    @Override
    public CompanyRoleDTO save(CompanyRoleDTO companyRoleDTO) {
        log.debug("Request to save CompanyRole : {}", companyRoleDTO);
        CompanyRole companyRole = companyRoleMapper.toEntity(companyRoleDTO);
        companyRole = companyRoleRepository.save(companyRole);
        return companyRoleMapper.toDto(companyRole);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyRoles");
        return companyRoleRepository.findAll(pageable)
            .map(companyRoleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyRoleDTO> findOne(Long id) {
        log.debug("Request to get CompanyRole : {}", id);
        return companyRoleRepository.findById(id)
            .map(companyRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyRole : {}", id);
        companyRoleRepository.deleteById(id);
    }

    @Override
    public List<CompanyRoleDTO> getAllByCompanyId(Long id) {
        return companyRoleRepository.getAllByCompanyId(id)
                .stream().map(companyRoleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CompanyRoleDTO> saveAll(List<CompanyRoleDTO> lst, Long companyId) {
        List<CompanyRole> selected = lst.stream().map(companyRoleMapper::toEntity).collect(Collectors.toList());
        List<CompanyRole> origin = companyRoleRepository.getAllByCompanyId(companyId);
        Iterator<CompanyRole> i = origin.listIterator();
        while (i.hasNext()) {
            CompanyRole nextOrigin = i.next();
            boolean isUncheck = true;
            Iterator<CompanyRole> j = selected.listIterator();
            while (j.hasNext()) {
                CompanyRole nextSelected = j.next();
                if (nextSelected.getCompanyId().equals(nextOrigin.getCompanyId())
                        && nextSelected.getRoleId().equals(nextOrigin.getRoleId())) {
                    j.remove();
                    isUncheck = false;
                    break;
                }
            }
            if (!isUncheck) {
                i.remove();
            }
        }
        companyRoleRepository.deleteInBatch(origin);
        selected = companyRoleRepository.saveAll(selected);
        return companyRoleMapper.toDto(selected);
    }
}
