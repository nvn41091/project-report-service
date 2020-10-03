package com.nvn41091.service.impl;

import com.nvn41091.service.UserRoleService;
import com.nvn41091.domain.UserRole;
import com.nvn41091.repository.UserRoleRepository;
import com.nvn41091.service.dto.UserRoleDTO;
import com.nvn41091.service.mapper.UserRoleMapper;
import com.nvn41091.utils.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserRole}.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    private final UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserRoleDTO save(UserRoleDTO userRoleDTO) {
        log.debug("Request to save UserRole : {}", userRoleDTO);
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRoles");
        return userRoleRepository.findAll(pageable)
            .map(userRoleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserRoleDTO> findOne(Long id) {
        log.debug("Request to get UserRole : {}", id);
        return userRoleRepository.findById(id)
            .map(userRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRole : {}", id);
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRoleDTO> getAllByUserId(Long id) {
        return userRoleRepository.getAllByUserId(id).stream().map(userRoleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SimpleGrantedAuthority> getRoleByUserId(Long id) {
        return userRoleRepository.getUserRole(id)
                .stream()
                .map(objects -> new SimpleGrantedAuthority(DataUtil.safeToString(objects[0]) + "#" + DataUtil.safeToString(objects[1])))
                .collect(Collectors.toList());
    }
}
