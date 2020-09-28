package com.nvn41091.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class RoleModuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleModule.class);
        RoleModule roleModule1 = new RoleModule();
        roleModule1.setId(1L);
        RoleModule roleModule2 = new RoleModule();
        roleModule2.setId(roleModule1.getId());
        assertThat(roleModule1).isEqualTo(roleModule2);
        roleModule2.setId(2L);
        assertThat(roleModule1).isNotEqualTo(roleModule2);
        roleModule1.setId(null);
        assertThat(roleModule1).isNotEqualTo(roleModule2);
    }
}
