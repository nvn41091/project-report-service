package com.nvn41091.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class ModuleActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleActionDTO.class);
        ModuleActionDTO moduleActionDTO1 = new ModuleActionDTO();
        moduleActionDTO1.setId(1L);
        ModuleActionDTO moduleActionDTO2 = new ModuleActionDTO();
        assertThat(moduleActionDTO1).isNotEqualTo(moduleActionDTO2);
        moduleActionDTO2.setId(moduleActionDTO1.getId());
        assertThat(moduleActionDTO1).isEqualTo(moduleActionDTO2);
        moduleActionDTO2.setId(2L);
        assertThat(moduleActionDTO1).isNotEqualTo(moduleActionDTO2);
        moduleActionDTO1.setId(null);
        assertThat(moduleActionDTO1).isNotEqualTo(moduleActionDTO2);
    }
}
