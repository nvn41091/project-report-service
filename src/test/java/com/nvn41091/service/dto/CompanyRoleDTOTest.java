package com.nvn41091.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class CompanyRoleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyRoleDTO.class);
        CompanyRoleDTO companyRoleDTO1 = new CompanyRoleDTO();
        companyRoleDTO1.setId(1L);
        CompanyRoleDTO companyRoleDTO2 = new CompanyRoleDTO();
        assertThat(companyRoleDTO1).isNotEqualTo(companyRoleDTO2);
        companyRoleDTO2.setId(companyRoleDTO1.getId());
        assertThat(companyRoleDTO1).isEqualTo(companyRoleDTO2);
        companyRoleDTO2.setId(2L);
        assertThat(companyRoleDTO1).isNotEqualTo(companyRoleDTO2);
        companyRoleDTO1.setId(null);
        assertThat(companyRoleDTO1).isNotEqualTo(companyRoleDTO2);
    }
}
