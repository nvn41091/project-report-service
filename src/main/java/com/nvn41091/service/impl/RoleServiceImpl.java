package com.nvn41091.service.impl;

import com.nvn41091.domain.CompanyRole;
import com.nvn41091.repository.RoleModuleRepository;
import com.nvn41091.repository.UserRoleRepository;
import com.nvn41091.service.RoleService;
import com.nvn41091.domain.Role;
import com.nvn41091.repository.RoleRepository;
import com.nvn41091.service.dto.CompanyRoleDTO;
import com.nvn41091.service.dto.RoleDTO;
import com.nvn41091.service.mapper.RoleMapper;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Role}.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final RoleModuleRepository roleModuleRepository;

    private final UserRoleRepository userRoleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, RoleModuleRepository roleModuleRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.roleModuleRepository = roleModuleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        if (roleDTO.getId() != null) {
            // Validate cap nhat
            if (roleRepository.findAllById(roleDTO.getId()).size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.role.notFound"), "role", "role.notFound");
            }
        } else {
            // Validate them moi
        }
        if (roleRepository.findAllByCodeAndIdNotEqual(roleDTO.getCode(), roleDTO.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.role.codeExist"), "role", "role.codeExist");
        }
        roleDTO.setUpdateTime(Instant.now());
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleRepository.findAll(pageable)
            .map(roleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> doSearch(RoleDTO roleDTO, Pageable pageable) {
        return roleRepository.doSearch(DataUtil.makeLikeParam(roleDTO.getCode()),
                DataUtil.makeLikeParam(roleDTO.getName()),
                roleDTO.isStatus(), pageable)
                .map(roleMapper::toDto);
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDTO> findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        return roleRepository.findById(id)
            .map(roleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        if (roleRepository.findAllById(id).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.role.notFound"), "role", "role.notFound");
        }
        roleRepository.deleteById(id);
        roleModuleRepository.deleteAllByRoleId(id);
        userRoleRepository.deleteAllByRoleId(id);
    }

    @Override
    public List<RoleDTO> searchByCodeOrName(RoleDTO roleDTO) {
        return roleRepository.searchByCodeOrName(roleDTO.getName())
                .stream().map(roleMapper::toDto).collect(Collectors.toList());
    }
}
