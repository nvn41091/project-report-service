package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.CompanyRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyRole} and its DTO {@link CompanyRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyRoleMapper extends EntityMapper<CompanyRoleDTO, CompanyRole> {



    default CompanyRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyRole companyRole = new CompanyRole();
        companyRole.setId(id);
        return companyRole;
    }
}
