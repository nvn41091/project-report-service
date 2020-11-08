package com.nvn41091.web.rest;

import com.nvn41091.run.ReportApp;
import com.nvn41091.domain.ProjectInformation;
import com.nvn41091.repository.ProjectInformationRepository;
import com.nvn41091.service.ProjectInformationService;
import com.nvn41091.service.dto.ProjectInformationDTO;
import com.nvn41091.service.mapper.ProjectInformationMapper;

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
 * Integration tests for the {@link ProjectInformationResource} REST controller.
 */
@SpringBootTest(classes = ReportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectInformationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE_PLAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE_PLAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTUAL_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTUAL_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_MONEY = 1L;
    private static final Long UPDATED_MONEY = 2L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_STATUS = 1L;
    private static final Long UPDATED_STATUS = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProjectInformationRepository projectInformationRepository;

    @Autowired
    private ProjectInformationMapper projectInformationMapper;

    @Autowired
    private ProjectInformationService projectInformationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectInformationMockMvc;

    private ProjectInformation projectInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectInformation createEntity(EntityManager em) {
        ProjectInformation projectInformation = new ProjectInformation()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDatePlan(DEFAULT_END_DATE_PLAN)
            .actualEndTime(DEFAULT_ACTUAL_END_TIME)
            .money(DEFAULT_MONEY)
            .customerId(DEFAULT_CUSTOMER_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .updateTime(DEFAULT_UPDATE_TIME);
        return projectInformation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectInformation createUpdatedEntity(EntityManager em) {
        ProjectInformation projectInformation = new ProjectInformation()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDatePlan(UPDATED_END_DATE_PLAN)
            .actualEndTime(UPDATED_ACTUAL_END_TIME)
            .money(UPDATED_MONEY)
            .customerId(UPDATED_CUSTOMER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .updateTime(UPDATED_UPDATE_TIME);
        return projectInformation;
    }

    @BeforeEach
    public void initTest() {
        projectInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectInformation() throws Exception {
        int databaseSizeBeforeCreate = projectInformationRepository.findAll().size();
        // Create the ProjectInformation
        ProjectInformationDTO projectInformationDTO = projectInformationMapper.toDto(projectInformation);
        restProjectInformationMockMvc.perform(post("/api/project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the ProjectInformation in the database
        List<ProjectInformation> projectInformationList = projectInformationRepository.findAll();
        assertThat(projectInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectInformation testProjectInformation = projectInformationList.get(projectInformationList.size() - 1);
        assertThat(testProjectInformation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectInformation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProjectInformation.getEndDatePlan()).isEqualTo(DEFAULT_END_DATE_PLAN);
        assertThat(testProjectInformation.getActualEndTime()).isEqualTo(DEFAULT_ACTUAL_END_TIME);
        assertThat(testProjectInformation.getMoney()).isEqualTo(DEFAULT_MONEY);
        assertThat(testProjectInformation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testProjectInformation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testProjectInformation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectInformation.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createProjectInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectInformationRepository.findAll().size();

        // Create the ProjectInformation with an existing ID
        projectInformation.setId(1L);
        ProjectInformationDTO projectInformationDTO = projectInformationMapper.toDto(projectInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectInformationMockMvc.perform(post("/api/project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectInformation in the database
        List<ProjectInformation> projectInformationList = projectInformationRepository.findAll();
        assertThat(projectInformationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectInformations() throws Exception {
        // Initialize the database
        projectInformationRepository.saveAndFlush(projectInformation);

        // Get all the projectInformationList
        restProjectInformationMockMvc.perform(get("/api/project-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDatePlan").value(hasItem(DEFAULT_END_DATE_PLAN.toString())))
            .andExpect(jsonPath("$.[*].actualEndTime").value(hasItem(DEFAULT_ACTUAL_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectInformation() throws Exception {
        // Initialize the database
        projectInformationRepository.saveAndFlush(projectInformation);

        // Get the projectInformation
        restProjectInformationMockMvc.perform(get("/api/project-informations/{id}", projectInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectInformation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDatePlan").value(DEFAULT_END_DATE_PLAN.toString()))
            .andExpect(jsonPath("$.actualEndTime").value(DEFAULT_ACTUAL_END_TIME.toString()))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProjectInformation() throws Exception {
        // Get the projectInformation
        restProjectInformationMockMvc.perform(get("/api/project-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectInformation() throws Exception {
        // Initialize the database
        projectInformationRepository.saveAndFlush(projectInformation);

        int databaseSizeBeforeUpdate = projectInformationRepository.findAll().size();

        // Update the projectInformation
        ProjectInformation updatedProjectInformation = projectInformationRepository.findById(projectInformation.getId()).get();
        // Disconnect from session so that the updates on updatedProjectInformation are not directly saved in db
        em.detach(updatedProjectInformation);
        updatedProjectInformation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDatePlan(UPDATED_END_DATE_PLAN)
            .actualEndTime(UPDATED_ACTUAL_END_TIME)
            .money(UPDATED_MONEY)
            .customerId(UPDATED_CUSTOMER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .updateTime(UPDATED_UPDATE_TIME);
        ProjectInformationDTO projectInformationDTO = projectInformationMapper.toDto(updatedProjectInformation);

        restProjectInformationMockMvc.perform(put("/api/project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectInformationDTO)))
            .andExpect(status().isOk());

        // Validate the ProjectInformation in the database
        List<ProjectInformation> projectInformationList = projectInformationRepository.findAll();
        assertThat(projectInformationList).hasSize(databaseSizeBeforeUpdate);
        ProjectInformation testProjectInformation = projectInformationList.get(projectInformationList.size() - 1);
        assertThat(testProjectInformation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectInformation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProjectInformation.getEndDatePlan()).isEqualTo(UPDATED_END_DATE_PLAN);
        assertThat(testProjectInformation.getActualEndTime()).isEqualTo(UPDATED_ACTUAL_END_TIME);
        assertThat(testProjectInformation.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testProjectInformation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testProjectInformation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testProjectInformation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectInformation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectInformation.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectInformation() throws Exception {
        int databaseSizeBeforeUpdate = projectInformationRepository.findAll().size();

        // Create the ProjectInformation
        ProjectInformationDTO projectInformationDTO = projectInformationMapper.toDto(projectInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectInformationMockMvc.perform(put("/api/project-informations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectInformation in the database
        List<ProjectInformation> projectInformationList = projectInformationRepository.findAll();
        assertThat(projectInformationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectInformation() throws Exception {
        // Initialize the database
        projectInformationRepository.saveAndFlush(projectInformation);

        int databaseSizeBeforeDelete = projectInformationRepository.findAll().size();

        // Delete the projectInformation
        restProjectInformationMockMvc.perform(delete("/api/project-informations/{id}", projectInformation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectInformation> projectInformationList = projectInformationRepository.findAll();
        assertThat(projectInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
