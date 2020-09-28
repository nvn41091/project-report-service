package com.nvn41091.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nvn41091.web.rest.TestUtil;

public class ModuleActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleAction.class);
        ModuleAction moduleAction1 = new ModuleAction();
        moduleAction1.setId(1L);
        ModuleAction moduleAction2 = new ModuleAction();
        moduleAction2.setId(moduleAction1.getId());
        assertThat(moduleAction1).isEqualTo(moduleAction2);
        moduleAction2.setId(2L);
        assertThat(moduleAction1).isNotEqualTo(moduleAction2);
        moduleAction1.setId(null);
        assertThat(moduleAction1).isNotEqualTo(moduleAction2);
    }
}
