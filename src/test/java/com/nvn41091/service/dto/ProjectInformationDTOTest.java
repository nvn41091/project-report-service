package com.nvn41091.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class ProjectInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectInformationDTO.class);
        ProjectInformationDTO projectInformationDTO1 = new ProjectInformationDTO();
        projectInformationDTO1.setId(1L);
        ProjectInformationDTO projectInformationDTO2 = new ProjectInformationDTO();
        assertThat(projectInformationDTO1).isNotEqualTo(projectInformationDTO2);
        projectInformationDTO2.setId(projectInformationDTO1.getId());
        assertThat(projectInformationDTO1).isEqualTo(projectInformationDTO2);
        projectInformationDTO2.setId(2L);
        assertThat(projectInformationDTO1).isNotEqualTo(projectInformationDTO2);
        projectInformationDTO1.setId(null);
        assertThat(projectInformationDTO1).isNotEqualTo(projectInformationDTO2);
    }
}
