package com.nvn41091.web.rest;

import com.nvn41091.domain.ModuleAction;
import com.nvn41091.repository.ModuleActionRepository;
import com.nvn41091.run.ReportApp;
import com.nvn41091.service.ModuleActionService;
import com.nvn41091.service.dto.ModuleActionDTO;
import com.nvn41091.service.mapper.ModuleActionMapper;

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
 * Integration tests for the {@link ModuleActionResource} REST controller.
 */
@SpringBootTest(classes = ReportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ModuleActionResourceIT {

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final Long DEFAULT_ACTION_ID = 1L;
    private static final Long UPDATED_ACTION_ID = 2L;

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ModuleActionRepository moduleActionRepository;

    @Autowired
    private ModuleActionMapper moduleActionMapper;

    @Autowired
    private ModuleActionService moduleActionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModuleActionMockMvc;

    private ModuleAction moduleAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleAction createEntity(EntityManager em) {
        ModuleAction moduleAction = new ModuleAction()
            .moduleId(DEFAULT_MODULE_ID)
            .actionId(DEFAULT_ACTION_ID)
            .updateTime(DEFAULT_UPDATE_TIME);
        return moduleAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModuleAction createUpdatedEntity(EntityManager em) {
        ModuleAction moduleAction = new ModuleAction()
            .moduleId(UPDATED_MODULE_ID)
            .actionId(UPDATED_ACTION_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        return moduleAction;
    }

    @BeforeEach
    public void initTest() {
        moduleAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleAction() throws Exception {
        int databaseSizeBeforeCreate = moduleActionRepository.findAll().size();
        // Create the ModuleAction
        ModuleActionDTO moduleActionDTO = moduleActionMapper.toDto(moduleAction);
        restModuleActionMockMvc.perform(post("/api/module-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moduleActionDTO)))
            .andExpect(status().isCreated());

        // Validate the ModuleAction in the database
        List<ModuleAction> moduleActionList = moduleActionRepository.findAll();
        assertThat(moduleActionList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleAction testModuleAction = moduleActionList.get(moduleActionList.size() - 1);
        assertThat(testModuleAction.getModuleId()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testModuleAction.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testModuleAction.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createModuleActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleActionRepository.findAll().size();

        // Create the ModuleAction with an existing ID
        moduleAction.setId(1L);
        ModuleActionDTO moduleActionDTO = moduleActionMapper.toDto(moduleAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleActionMockMvc.perform(post("/api/module-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moduleActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleAction in the database
        List<ModuleAction> moduleActionList = moduleActionRepository.findAll();
        assertThat(moduleActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModuleActions() throws Exception {
        // Initialize the database
        moduleActionRepository.saveAndFlush(moduleAction);

        // Get all the moduleActionList
        restModuleActionMockMvc.perform(get("/api/module-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getModuleAction() throws Exception {
        // Initialize the database
        moduleActionRepository.saveAndFlush(moduleAction);

        // Get the moduleAction
        restModuleActionMockMvc.perform(get("/api/module-actions/{id}", moduleAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moduleAction.getId().intValue()))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingModuleAction() throws Exception {
        // Get the moduleAction
        restModuleActionMockMvc.perform(get("/api/module-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleAction() throws Exception {
        // Initialize the database
        moduleActionRepository.saveAndFlush(moduleAction);

        int databaseSizeBeforeUpdate = moduleActionRepository.findAll().size();

        // Update the moduleAction
        ModuleAction updatedModuleAction = moduleActionRepository.findById(moduleAction.getId()).get();
        // Disconnect from session so that the updates on updatedModuleAction are not directly saved in db
        em.detach(updatedModuleAction);
        updatedModuleAction
            .moduleId(UPDATED_MODULE_ID)
            .actionId(UPDATED_ACTION_ID)
            .updateTime(UPDATED_UPDATE_TIME);
        ModuleActionDTO moduleActionDTO = moduleActionMapper.toDto(updatedModuleAction);

        restModuleActionMockMvc.perform(put("/api/module-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moduleActionDTO)))
            .andExpect(status().isOk());

        // Validate the ModuleAction in the database
        List<ModuleAction> moduleActionList = moduleActionRepository.findAll();
        assertThat(moduleActionList).hasSize(databaseSizeBeforeUpdate);
        ModuleAction testModuleAction = moduleActionList.get(moduleActionList.size() - 1);
        assertThat(testModuleAction.getModuleId()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testModuleAction.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testModuleAction.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleAction() throws Exception {
        int databaseSizeBeforeUpdate = moduleActionRepository.findAll().size();

        // Create the ModuleAction
        ModuleActionDTO moduleActionDTO = moduleActionMapper.toDto(moduleAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleActionMockMvc.perform(put("/api/module-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moduleActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleAction in the database
        List<ModuleAction> moduleActionList = moduleActionRepository.findAll();
        assertThat(moduleActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModuleAction() throws Exception {
        // Initialize the database
        moduleActionRepository.saveAndFlush(moduleAction);

        int databaseSizeBeforeDelete = moduleActionRepository.findAll().size();

        // Delete the moduleAction
        restModuleActionMockMvc.perform(delete("/api/module-actions/{id}", moduleAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModuleAction> moduleActionList = moduleActionRepository.findAll();
        assertThat(moduleActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
