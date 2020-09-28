package com.nvn41091.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class RoleModuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleModuleDTO.class);
        RoleModuleDTO roleModuleDTO1 = new RoleModuleDTO();
        roleModuleDTO1.setId(1L);
        RoleModuleDTO roleModuleDTO2 = new RoleModuleDTO();
        assertThat(roleModuleDTO1).isNotEqualTo(roleModuleDTO2);
        roleModuleDTO2.setId(roleModuleDTO1.getId());
        assertThat(roleModuleDTO1).isEqualTo(roleModuleDTO2);
        roleModuleDTO2.setId(2L);
        assertThat(roleModuleDTO1).isNotEqualTo(roleModuleDTO2);
        roleModuleDTO1.setId(null);
        assertThat(roleModuleDTO1).isNotEqualTo(roleModuleDTO2);
    }
}
