package com.nvn41091.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class AppParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppParamDTO.class);
        AppParamDTO appParamDTO1 = new AppParamDTO();
        appParamDTO1.setId(1L);
        AppParamDTO appParamDTO2 = new AppParamDTO();
        assertThat(appParamDTO1).isNotEqualTo(appParamDTO2);
        appParamDTO2.setId(appParamDTO1.getId());
        assertThat(appParamDTO1).isEqualTo(appParamDTO2);
        appParamDTO2.setId(2L);
        assertThat(appParamDTO1).isNotEqualTo(appParamDTO2);
        appParamDTO1.setId(null);
        assertThat(appParamDTO1).isNotEqualTo(appParamDTO2);
    }
}
