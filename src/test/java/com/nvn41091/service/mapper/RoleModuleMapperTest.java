package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RoleModuleMapperTest {

    private RoleModuleMapper roleModuleMapper;

    @BeforeEach
    public void setUp() {
        roleModuleMapper = new RoleModuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(roleModuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(roleModuleMapper.fromId(null)).isNull();
    }
}
