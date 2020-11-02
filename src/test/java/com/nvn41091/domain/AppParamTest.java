package com.nvn41091.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class AppParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppParam.class);
        AppParam appParam1 = new AppParam();
        appParam1.setId(1L);
        AppParam appParam2 = new AppParam();
        appParam2.setId(appParam1.getId());
        assertThat(appParam1).isEqualTo(appParam2);
        appParam2.setId(2L);
        assertThat(appParam1).isNotEqualTo(appParam2);
        appParam1.setId(null);
        assertThat(appParam1).isNotEqualTo(appParam2);
    }
}
