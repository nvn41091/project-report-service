package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppParamMapperTest {

    private AppParamMapper appParamMapper;

    @BeforeEach
    public void setUp() {
        appParamMapper = new AppParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(appParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appParamMapper.fromId(null)).isNull();
    }
}
