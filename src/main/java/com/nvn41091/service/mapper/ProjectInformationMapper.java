package com.nvn41091.service.mapper;


import com.nvn41091.domain.*;
import com.nvn41091.service.dto.ProjectInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectInformation} and its DTO {@link ProjectInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectInformationMapper extends EntityMapper<ProjectInformationDTO, ProjectInformation> {



    default ProjectInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectInformation projectInformation = new ProjectInformation();
        projectInformation.setId(id);
        return projectInformation;
    }
}
