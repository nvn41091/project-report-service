package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ModuleActionMapperTest {

    private ModuleActionMapper moduleActionMapper;

    @BeforeEach
    public void setUp() {
        moduleActionMapper = new ModuleActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(moduleActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(moduleActionMapper.fromId(null)).isNull();
    }
}
