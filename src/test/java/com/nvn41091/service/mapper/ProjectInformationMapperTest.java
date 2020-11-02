package com.nvn41091.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectInformationMapperTest {

    private ProjectInformationMapper projectInformationMapper;

    @BeforeEach
    public void setUp() {
        projectInformationMapper = new ProjectInformationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectInformationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectInformationMapper.fromId(null)).isNull();
    }
}
