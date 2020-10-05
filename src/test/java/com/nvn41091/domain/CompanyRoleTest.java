package com.nvn41091.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class CompanyRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyRole.class);
        CompanyRole companyRole1 = new CompanyRole();
        companyRole1.setId(1L);
        CompanyRole companyRole2 = new CompanyRole();
        companyRole2.setId(companyRole1.getId());
        assertThat(companyRole1).isEqualTo(companyRole2);
        companyRole2.setId(2L);
        assertThat(companyRole1).isNotEqualTo(companyRole2);
        companyRole1.setId(null);
        assertThat(companyRole1).isNotEqualTo(companyRole2);
    }
}
