package com.nvn41091.web.rest;

import com.nvn41091.domain.UserRole;
import com.nvn41091.repository.UserRoleRepository;
import com.nvn41091.run.ReportApp;
import com.nvn41091.service.UserRoleService;
import com.nvn41091.service.dto.UserRoleDTO;
import com.nvn41091.service.mapper.UserRoleMapper;

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
 * Integration tests for the {@link UserRoleResource} REST controller.
 */
@SpringBootTest(classes = ReportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserRoleResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRoleMockMvc;

    private UserRole userRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRole createEntity(EntityManager em) {
        UserRole userRole = new UserRole()
            .userId(DEFAULT_USER_ID)
            .roleId(DEFAULT_ROLE_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .updateTime(DEFAULT_UPDATE_TIME);
        return userRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRole createUpdatedEntity(EntityManager em) {
        UserRole userRole = new UserRole()
            .userId(UPDATED_USER_ID)
            .roleId(UPDATED_ROLE_ID)
            .companyId(UPDATED_COMPANY_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        return userRole;
    }

    @BeforeEach
    public void initTest() {
        userRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserRole() throws Exception {
        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();
        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);
        restUserRoleMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate + 1);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserRole.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testUserRole.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testUserRole.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createUserRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRoleRepository.findAll().size();

        // Create the UserRole with an existing ID
        userRole.setId(1L);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRoleMockMvc.perform(post("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserRoles() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get all the userRoleList
        restUserRoleMockMvc.perform(get("/api/user-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        // Get the userRole
        restUserRoleMockMvc.perform(get("/api/user-roles/{id}", userRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRole.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserRole() throws Exception {
        // Get the userRole
        restUserRoleMockMvc.perform(get("/api/user-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Update the userRole
        UserRole updatedUserRole = userRoleRepository.findById(userRole.getId()).get();
        // Disconnect from session so that the updates on updatedUserRole are not directly saved in db
        em.detach(updatedUserRole);
        updatedUserRole
            .userId(UPDATED_USER_ID)
            .roleId(UPDATED_ROLE_ID)
            .companyId(UPDATED_COMPANY_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(updatedUserRole);

        restUserRoleMockMvc.perform(put("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isOk());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
        UserRole testUserRole = userRoleList.get(userRoleList.size() - 1);
        assertThat(testUserRole.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserRole.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testUserRole.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testUserRole.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserRole() throws Exception {
        int databaseSizeBeforeUpdate = userRoleRepository.findAll().size();

        // Create the UserRole
        UserRoleDTO userRoleDTO = userRoleMapper.toDto(userRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRoleMockMvc.perform(put("/api/user-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserRole in the database
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserRole() throws Exception {
        // Initialize the database
        userRoleRepository.saveAndFlush(userRole);

        int databaseSizeBeforeDelete = userRoleRepository.findAll().size();

        // Delete the userRole
        restUserRoleMockMvc.perform(delete("/api/user-roles/{id}", userRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRole> userRoleList = userRoleRepository.findAll();
        assertThat(userRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
