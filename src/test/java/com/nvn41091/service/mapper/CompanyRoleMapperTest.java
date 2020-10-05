package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyRoleMapperTest {

    private CompanyRoleMapper companyRoleMapper;

    @BeforeEach
    public void setUp() {
        companyRoleMapper = new CompanyRoleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyRoleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyRoleMapper.fromId(null)).isNull();
    }
}
