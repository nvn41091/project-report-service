package com.nvn41091.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class ProjectInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectInformation.class);
        ProjectInformation projectInformation1 = new ProjectInformation();
        projectInformation1.setId(1L);
        ProjectInformation projectInformation2 = new ProjectInformation();
        projectInformation2.setId(projectInformation1.getId());
        assertThat(projectInformation1).isEqualTo(projectInformation2);
        projectInformation2.setId(2L);
        assertThat(projectInformation1).isNotEqualTo(projectInformation2);
        projectInformation1.setId(null);
        assertThat(projectInformation1).isNotEqualTo(projectInformation2);
    }
}
