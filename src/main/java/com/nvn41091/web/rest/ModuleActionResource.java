package com.nvn41091.web.rest;

import com.nvn41091.service.ModuleActionService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.ModuleActionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nvn41091.domain.ModuleAction}.
 */
@RestController
@RequestMapping("/api")
public class ModuleActionResource {

    private final Logger log = LoggerFactory.getLogger(ModuleActionResource.class);

    private static final String ENTITY_NAME = "moduleAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleActionService moduleActionService;

    public ModuleActionResource(ModuleActionService moduleActionService) {
        this.moduleActionService = moduleActionService;
    }

    /**
     * {@code POST  /module-actions} : Create a new moduleAction.
     *
     * @param moduleActionDTO the moduleActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleActionDTO, or with status {@code 400 (Bad Request)} if the moduleAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-actions")
    public ResponseEntity<ModuleActionDTO> createModuleAction(@RequestBody ModuleActionDTO moduleActionDTO) throws URISyntaxException {
        log.debug("REST request to save ModuleAction : {}", moduleActionDTO);
        if (moduleActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleActionDTO result = moduleActionService.save(moduleActionDTO);
        return ResponseEntity.created(new URI("/api/module-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /module-actions} : Updates an existing moduleAction.
     *
     * @param moduleActionDTO the moduleActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleActionDTO,
     * or with status {@code 400 (Bad Request)} if the moduleActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-actions")
    public ResponseEntity<ModuleActionDTO> updateModuleAction(@RequestBody ModuleActionDTO moduleActionDTO) throws URISyntaxException {
        log.debug("REST request to update ModuleAction : {}", moduleActionDTO);
        if (moduleActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleActionDTO result = moduleActionService.save(moduleActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /module-actions} : get all the moduleActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moduleActions in body.
     */
    @GetMapping("/module-actions")
    public ResponseEntity<List<ModuleActionDTO>> getAllModuleActions(Pageable pageable) {
        log.debug("REST request to get a page of ModuleActions");
        Page<ModuleActionDTO> page = moduleActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /module-actions/:id} : get the "id" moduleAction.
     *
     * @param id the id of the moduleActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-actions/{id}")
    public ResponseEntity<ModuleActionDTO> getModuleAction(@PathVariable Long id) {
        log.debug("REST request to get ModuleAction : {}", id);
        Optional<ModuleActionDTO> moduleActionDTO = moduleActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleActionDTO);
    }

    /**
     * {@code DELETE  /module-actions/:id} : delete the "id" moduleAction.
     *
     * @param id the id of the moduleActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-actions/{id}")
    public ResponseEntity<Void> deleteModuleAction(@PathVariable Long id) {
        log.debug("REST request to delete ModuleAction : {}", id);
        moduleActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
