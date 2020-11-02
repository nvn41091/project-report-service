package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.AppParamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppParam} and its DTO {@link AppParamDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppParamMapper extends EntityMapper<AppParamDTO, AppParam> {



    default AppParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppParam appParam = new AppParam();
        appParam.setId(id);
        return appParam;
    }
}
