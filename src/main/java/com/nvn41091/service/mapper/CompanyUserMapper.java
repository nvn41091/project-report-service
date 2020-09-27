package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.CompanyUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyUser} and its DTO {@link CompanyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyUserMapper extends EntityMapper<CompanyUserDTO, CompanyUser> {



    default CompanyUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyUser companyUser = new CompanyUser();
        companyUser.setId(id);
        return companyUser;
    }
}
