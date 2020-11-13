package com.nvn41091.service.impl;

import com.nvn41091.configuration.Constants;
import com.nvn41091.domain.RoleModule;
import com.nvn41091.repository.RoleModuleRepository;
import com.nvn41091.security.SecurityUtils;
import com.nvn41091.service.RoleModuleService;
import com.nvn41091.service.dto.RoleModuleDTO;
import com.nvn41091.service.dto.TreeViewDTO;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.mapper.RoleModuleMapper;
import com.nvn41091.utils.DataUtil;
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
 * Service Implementation for managing {@link RoleModule}.
 */
@Service
@Transactional
public class RoleModuleServiceImpl implements RoleModuleService {

    private final Logger log = LoggerFactory.getLogger(RoleModuleServiceImpl.class);

    private final RoleModuleRepository roleModuleRepository;

    private final RoleModuleMapper roleModuleMapper;

    public RoleModuleServiceImpl(RoleModuleRepository roleModuleRepository, RoleModuleMapper roleModuleMapper) {
        this.roleModuleRepository = roleModuleRepository;
        this.roleModuleMapper = roleModuleMapper;
    }

    @Override
    public List<RoleModuleDTO> save(List<RoleModuleDTO> lst, Long roleId) {
        log.debug("Request to save list RoleModule : {}", lst);
        List<RoleModule> selected = lst.stream().map(roleModuleMapper::toEntity).collect(Collectors.toList());
        List<RoleModule> origin = roleModuleRepository.getAllByRoleId(roleId);
        Iterator<RoleModule> i = origin.listIterator();
        while (i.hasNext()) {
            RoleModule nextOrigin = i.next();
            boolean isUncheck = true;
            Iterator<RoleModule> j = selected.listIterator();
            while (j.hasNext()) {
                RoleModule nextSelected = j.next();
                if (nextSelected.getModuleId().equals(nextOrigin.getModuleId())
                        && nextSelected.getActionId().equals(nextOrigin.getActionId())
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
        roleModuleRepository.deleteInBatch(origin);
        selected = roleModuleRepository.saveAll(selected);
        return roleModuleMapper.toDto(selected);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleModules");
        return roleModuleRepository.findAll(pageable)
                .map(roleModuleMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RoleModuleDTO> findOne(Long id) {
        log.debug("Request to get RoleModule : {}", id);
        return roleModuleRepository.findById(id)
                .map(roleModuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleModule : {}", id);
        roleModuleRepository.deleteById(id);
    }

    @Override
    public List<TreeViewDTO> getALl(Long id) {
        UserDTO current = SecurityUtils.getCurrentUser().get();
        return roleModuleRepository.getAllModuleAndActionByRoleId(id, current.getCompanyId(), Constants.CONST_COMPANY_ID_ADMIN, Constants.CONST_ROLE_ID_FOR_ADMIN)
                .stream().map(objects -> new TreeViewDTO(DataUtil.safeToString(objects[0]),
                        DataUtil.safeToLong(objects[2]),
                        DataUtil.safeToString(objects[1]),
                        DataUtil.safeToBoolean(objects[3]))).collect(Collectors.toList());
    }
}
