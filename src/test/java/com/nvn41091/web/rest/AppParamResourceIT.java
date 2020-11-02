package com.nvn41091.web.rest;

import com.nvn41091.domain.AppParam;
import com.nvn41091.repository.AppParamRepository;
import com.nvn41091.run.ReportApp;
import com.nvn41091.service.AppParamService;
import com.nvn41091.service.dto.AppParamDTO;
import com.nvn41091.service.mapper.AppParamMapper;

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
 * Integration tests for the {@link AppParamResource} REST controller.
 */
@SpringBootTest(classes = ReportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppParamResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private AppParamRepository appParamRepository;

    @Autowired
    private AppParamMapper appParamMapper;

    @Autowired
    private AppParamService appParamService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppParamMockMvc;

    private AppParam appParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppParam createEntity(EntityManager em) {
        AppParam appParam = new AppParam()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .updateTime(DEFAULT_UPDATE_TIME)
            .status(DEFAULT_STATUS);
        return appParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppParam createUpdatedEntity(EntityManager em) {
        AppParam appParam = new AppParam()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS);
        return appParam;
    }

    @BeforeEach
    public void initTest() {
        appParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppParam() throws Exception {
        int databaseSizeBeforeCreate = appParamRepository.findAll().size();
        // Create the AppParam
        AppParamDTO appParamDTO = appParamMapper.toDto(appParam);
        restAppParamMockMvc.perform(post("/api/app-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appParamDTO)))
            .andExpect(status().isCreated());

        // Validate the AppParam in the database
        List<AppParam> appParamList = appParamRepository.findAll();
        assertThat(appParamList).hasSize(databaseSizeBeforeCreate + 1);
        AppParam testAppParam = appParamList.get(appParamList.size() - 1);
        assertThat(testAppParam.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAppParam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppParam.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppParam.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAppParam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppParam.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testAppParam.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAppParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appParamRepository.findAll().size();

        // Create the AppParam with an existing ID
        appParam.setId(1L);
        AppParamDTO appParamDTO = appParamMapper.toDto(appParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppParamMockMvc.perform(post("/api/app-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppParam in the database
        List<AppParam> appParamList = appParamRepository.findAll();
        assertThat(appParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppParams() throws Exception {
        // Initialize the database
        appParamRepository.saveAndFlush(appParam);

        // Get all the appParamList
        restAppParamMockMvc.perform(get("/api/app-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAppParam() throws Exception {
        // Initialize the database
        appParamRepository.saveAndFlush(appParam);

        // Get the appParam
        restAppParamMockMvc.perform(get("/api/app-params/{id}", appParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appParam.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAppParam() throws Exception {
        // Get the appParam
        restAppParamMockMvc.perform(get("/api/app-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppParam() throws Exception {
        // Initialize the database
        appParamRepository.saveAndFlush(appParam);

        int databaseSizeBeforeUpdate = appParamRepository.findAll().size();

        // Update the appParam
        AppParam updatedAppParam = appParamRepository.findById(appParam.getId()).get();
        // Disconnect from session so that the updates on updatedAppParam are not directly saved in db
        em.detach(updatedAppParam);
        updatedAppParam
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .updateTime(UPDATED_UPDATE_TIME)
            .status(UPDATED_STATUS);
        AppParamDTO appParamDTO = appParamMapper.toDto(updatedAppParam);

        restAppParamMockMvc.perform(put("/api/app-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appParamDTO)))
            .andExpect(status().isOk());

        // Validate the AppParam in the database
        List<AppParam> appParamList = appParamRepository.findAll();
        assertThat(appParamList).hasSize(databaseSizeBeforeUpdate);
        AppParam testAppParam = appParamList.get(appParamList.size() - 1);
        assertThat(testAppParam.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAppParam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppParam.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppParam.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAppParam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppParam.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testAppParam.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAppParam() throws Exception {
        int databaseSizeBeforeUpdate = appParamRepository.findAll().size();

        // Create the AppParam
        AppParamDTO appParamDTO = appParamMapper.toDto(appParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppParamMockMvc.perform(put("/api/app-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppParam in the database
        List<AppParam> appParamList = appParamRepository.findAll();
        assertThat(appParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppParam() throws Exception {
        // Initialize the database
        appParamRepository.saveAndFlush(appParam);

        int databaseSizeBeforeDelete = appParamRepository.findAll().size();

        // Delete the appParam
        restAppParamMockMvc.perform(delete("/api/app-params/{id}", appParam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppParam> appParamList = appParamRepository.findAll();
        assertThat(appParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
