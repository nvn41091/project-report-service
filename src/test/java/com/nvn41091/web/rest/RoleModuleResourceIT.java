package com.nvn41091.web.rest;

import com.nvn41091.domain.RoleModule;
import com.nvn41091.repository.RoleModuleRepository;
import com.nvn41091.run.SecurityJwtApplication;
import com.nvn41091.service.RoleModuleService;
import com.nvn41091.service.dto.RoleModuleDTO;
import com.nvn41091.service.mapper.RoleModuleMapper;

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
 * Integration tests for the {@link RoleModuleResource} REST controller.
 */
@SpringBootTest(classes = SecurityJwtApplication.class)
@AutoConfigureMockMvc
@WithMockUser
public class RoleModuleResourceIT {

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final Long DEFAULT_ACTION_ID = 1L;
    private static final Long UPDATED_ACTION_ID = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RoleModuleRepository roleModuleRepository;

    @Autowired
    private RoleModuleMapper roleModuleMapper;

    @Autowired
    private RoleModuleService roleModuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleModuleMockMvc;

    private RoleModule roleModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleModule createEntity(EntityManager em) {
        RoleModule roleModule = new RoleModule()
            .roleId(DEFAULT_ROLE_ID)
            .moduleId(DEFAULT_MODULE_ID)
            .actionId(DEFAULT_ACTION_ID)
            .updateTime(DEFAULT_UPDATE_TIME);
        return roleModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleModule createUpdatedEntity(EntityManager em) {
        RoleModule roleModule = new RoleModule()
            .roleId(UPDATED_ROLE_ID)
            .moduleId(UPDATED_MODULE_ID)
            .actionId(UPDATED_ACTION_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        return roleModule;
    }

    @BeforeEach
    public void initTest() {
        roleModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleModule() throws Exception {
        int databaseSizeBeforeCreate = roleModuleRepository.findAll().size();
        // Create the RoleModule
        RoleModuleDTO roleModuleDTO = roleModuleMapper.toDto(roleModule);
        restRoleModuleMockMvc.perform(post("/api/role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the RoleModule in the database
        List<RoleModule> roleModuleList = roleModuleRepository.findAll();
        assertThat(roleModuleList).hasSize(databaseSizeBeforeCreate + 1);
        RoleModule testRoleModule = roleModuleList.get(roleModuleList.size() - 1);
        assertThat(testRoleModule.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testRoleModule.getModuleId()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testRoleModule.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testRoleModule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createRoleModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleModuleRepository.findAll().size();

        // Create the RoleModule with an existing ID
        roleModule.setId(1L);
        RoleModuleDTO roleModuleDTO = roleModuleMapper.toDto(roleModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleModuleMockMvc.perform(post("/api/role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleModule in the database
        List<RoleModule> roleModuleList = roleModuleRepository.findAll();
        assertThat(roleModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRoleModules() throws Exception {
        // Initialize the database
        roleModuleRepository.saveAndFlush(roleModule);

        // Get all the roleModuleList
        restRoleModuleMockMvc.perform(get("/api/role-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getRoleModule() throws Exception {
        // Initialize the database
        roleModuleRepository.saveAndFlush(roleModule);

        // Get the roleModule
        restRoleModuleMockMvc.perform(get("/api/role-modules/{id}", roleModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleModule.getId().intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRoleModule() throws Exception {
        // Get the roleModule
        restRoleModuleMockMvc.perform(get("/api/role-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleModule() throws Exception {
        // Initialize the database
        roleModuleRepository.saveAndFlush(roleModule);

        int databaseSizeBeforeUpdate = roleModuleRepository.findAll().size();

        // Update the roleModule
        RoleModule updatedRoleModule = roleModuleRepository.findById(roleModule.getId()).get();
        // Disconnect from session so that the updates on updatedRoleModule are not directly saved in db
        em.detach(updatedRoleModule);
        updatedRoleModule
            .roleId(UPDATED_ROLE_ID)
            .moduleId(UPDATED_MODULE_ID)
            .actionId(UPDATED_ACTION_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        RoleModuleDTO roleModuleDTO = roleModuleMapper.toDto(updatedRoleModule);

        restRoleModuleMockMvc.perform(put("/api/role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleModuleDTO)))
            .andExpect(status().isOk());

        // Validate the RoleModule in the database
        List<RoleModule> roleModuleList = roleModuleRepository.findAll();
        assertThat(roleModuleList).hasSize(databaseSizeBeforeUpdate);
        RoleModule testRoleModule = roleModuleList.get(roleModuleList.size() - 1);
        assertThat(testRoleModule.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testRoleModule.getModuleId()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testRoleModule.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testRoleModule.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleModule() throws Exception {
        int databaseSizeBeforeUpdate = roleModuleRepository.findAll().size();

        // Create the RoleModule
        RoleModuleDTO roleModuleDTO = roleModuleMapper.toDto(roleModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleModuleMockMvc.perform(put("/api/role-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleModule in the database
        List<RoleModule> roleModuleList = roleModuleRepository.findAll();
        assertThat(roleModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleModule() throws Exception {
        // Initialize the database
        roleModuleRepository.saveAndFlush(roleModule);

        int databaseSizeBeforeDelete = roleModuleRepository.findAll().size();

        // Delete the roleModule
        restRoleModuleMockMvc.perform(delete("/api/role-modules/{id}", roleModule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleModule> roleModuleList = roleModuleRepository.findAll();
        assertThat(roleModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
