package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.RoleModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleModule} and its DTO {@link RoleModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleModuleMapper extends EntityMapper<RoleModuleDTO, RoleModule> {



    default RoleModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoleModule roleModule = new RoleModule();
        roleModule.setId(id);
        return roleModule;
    }
}
