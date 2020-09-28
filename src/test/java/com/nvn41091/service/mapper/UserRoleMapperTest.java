package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRoleMapperTest {

    private UserRoleMapper userRoleMapper;

    @BeforeEach
    public void setUp() {
        userRoleMapper = new UserRoleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userRoleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userRoleMapper.fromId(null)).isNull();
    }
}
