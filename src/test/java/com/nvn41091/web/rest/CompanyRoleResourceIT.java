package com.nvn41091.web.rest;

import com.nvn41091.domain.CompanyRole;
import com.nvn41091.repository.CompanyRoleRepository;
import com.nvn41091.run.ReportApp;
import com.nvn41091.service.CompanyRoleService;
import com.nvn41091.service.dto.CompanyRoleDTO;
import com.nvn41091.service.mapper.CompanyRoleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompanyRoleResource} REST controller.
 */
@SpringBootTest(classes = ReportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyRoleResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompanyRoleRepository companyRoleRepository;

    @Autowired
    private CompanyRoleMapper companyRoleMapper;

    @Autowired
    private CompanyRoleService companyRoleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyRoleMockMvc;

    private CompanyRole companyRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyRole createEntity(EntityManager em) {
        CompanyRole companyRole = new CompanyRole()
            .companyId(DEFAULT_COMPANY_ID)
            .roleId(DEFAULT_ROLE_ID)
            .updateTime(DEFAULT_UPDATE_TIME);
        return companyRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyRole createUpdatedEntity(EntityManager em) {
        CompanyRole companyRole = new CompanyRole()
            .companyId(UPDATED_COMPANY_ID)
            .roleId(UPDATED_ROLE_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        return companyRole;
    }

    @BeforeEach
    public void initTest() {
        companyRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyRole() throws Exception {
        int databaseSizeBeforeCreate = companyRoleRepository.findAll().size();
        // Create the CompanyRole
        CompanyRoleDTO companyRoleDTO = companyRoleMapper.toDto(companyRole);
        restCompanyRoleMockMvc.perform(post("/api/company-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyRole in the database
        List<CompanyRole> companyRoleList = companyRoleRepository.findAll();
        assertThat(companyRoleList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyRole testCompanyRole = companyRoleList.get(companyRoleList.size() - 1);
        assertThat(testCompanyRole.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompanyRole.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testCompanyRole.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createCompanyRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRoleRepository.findAll().size();

        // Create the CompanyRole with an existing ID
        companyRole.setId(1L);
        CompanyRoleDTO companyRoleDTO = companyRoleMapper.toDto(companyRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyRoleMockMvc.perform(post("/api/company-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyRole in the database
        List<CompanyRole> companyRoleList = companyRoleRepository.findAll();
        assertThat(companyRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyRoles() throws Exception {
        // Initialize the database
        companyRoleRepository.saveAndFlush(companyRole);

        // Get all the companyRoleList
        restCompanyRoleMockMvc.perform(get("/api/company-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getCompanyRole() throws Exception {
        // Initialize the database
        companyRoleRepository.saveAndFlush(companyRole);

        // Get the companyRole
        restCompanyRoleMockMvc.perform(get("/api/company-roles/{id}", companyRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyRole.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCompanyRole() throws Exception {
        // Get the companyRole
        restCompanyRoleMockMvc.perform(get("/api/company-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyRole() throws Exception {
        // Initialize the database
        companyRoleRepository.saveAndFlush(companyRole);

        int databaseSizeBeforeUpdate = companyRoleRepository.findAll().size();

        // Update the companyRole
        CompanyRole updatedCompanyRole = companyRoleRepository.findById(companyRole.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyRole are not directly saved in db
        em.detach(updatedCompanyRole);
        updatedCompanyRole
            .companyId(UPDATED_COMPANY_ID)
            .roleId(UPDATED_ROLE_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        CompanyRoleDTO companyRoleDTO = companyRoleMapper.toDto(updatedCompanyRole);

        restCompanyRoleMockMvc.perform(put("/api/company-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyRoleDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyRole in the database
        List<CompanyRole> companyRoleList = companyRoleRepository.findAll();
        assertThat(companyRoleList).hasSize(databaseSizeBeforeUpdate);
        CompanyRole testCompanyRole = companyRoleList.get(companyRoleList.size() - 1);
        assertThat(testCompanyRole.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompanyRole.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCompanyRole.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyRole() throws Exception {
        int databaseSizeBeforeUpdate = companyRoleRepository.findAll().size();

        // Create the CompanyRole
        CompanyRoleDTO companyRoleDTO = companyRoleMapper.toDto(companyRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyRoleMockMvc.perform(put("/api/company-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyRole in the database
        List<CompanyRole> companyRoleList = companyRoleRepository.findAll();
        assertThat(companyRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyRole() throws Exception {
        // Initialize the database
        companyRoleRepository.saveAndFlush(companyRole);

        int databaseSizeBeforeDelete = companyRoleRepository.findAll().size();

        // Delete the companyRole
        restCompanyRoleMockMvc.perform(delete("/api/company-roles/{id}", companyRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyRole> companyRoleList = companyRoleRepository.findAll();
        assertThat(companyRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
