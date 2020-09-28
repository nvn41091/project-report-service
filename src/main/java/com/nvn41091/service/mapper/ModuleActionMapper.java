package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.ModuleActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModuleAction} and its DTO {@link ModuleActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModuleActionMapper extends EntityMapper<ModuleActionDTO, ModuleAction> {



    default ModuleAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModuleAction moduleAction = new ModuleAction();
        moduleAction.setId(id);
        return moduleAction;
    }
}
