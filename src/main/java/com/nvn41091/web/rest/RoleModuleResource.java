package com.nvn41091.web.rest;

import com.nvn41091.service.RoleModuleService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.RoleModuleDTO;

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
 * REST controller for managing {@link com.nvn41091.domain.RoleModule}.
 */
@RestController
@RequestMapping("/api")
public class RoleModuleResource {

    private final Logger log = LoggerFactory.getLogger(RoleModuleResource.class);

    private static final String ENTITY_NAME = "roleModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleModuleService roleModuleService;

    public RoleModuleResource(RoleModuleService roleModuleService) {
        this.roleModuleService = roleModuleService;
    }

    /**
     * {@code POST  /role-modules} : Create a new roleModule.
     *
     * @param roleModuleDTO the roleModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleModuleDTO, or with status {@code 400 (Bad Request)} if the roleModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-modules")
    public ResponseEntity<RoleModuleDTO> createRoleModule(@RequestBody RoleModuleDTO roleModuleDTO) throws URISyntaxException {
        log.debug("REST request to save RoleModule : {}", roleModuleDTO);
        if (roleModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleModuleDTO result = roleModuleService.save(roleModuleDTO);
        return ResponseEntity.created(new URI("/api/role-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-modules} : Updates an existing roleModule.
     *
     * @param roleModuleDTO the roleModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleModuleDTO,
     * or with status {@code 400 (Bad Request)} if the roleModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-modules")
    public ResponseEntity<RoleModuleDTO> updateRoleModule(@RequestBody RoleModuleDTO roleModuleDTO) throws URISyntaxException {
        log.debug("REST request to update RoleModule : {}", roleModuleDTO);
        if (roleModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleModuleDTO result = roleModuleService.save(roleModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /role-modules} : get all the roleModules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleModules in body.
     */
    @GetMapping("/role-modules")
    public ResponseEntity<List<RoleModuleDTO>> getAllRoleModules(Pageable pageable) {
        log.debug("REST request to get a page of RoleModules");
        Page<RoleModuleDTO> page = roleModuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /role-modules/:id} : get the "id" roleModule.
     *
     * @param id the id of the roleModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-modules/{id}")
    public ResponseEntity<RoleModuleDTO> getRoleModule(@PathVariable Long id) {
        log.debug("REST request to get RoleModule : {}", id);
        Optional<RoleModuleDTO> roleModuleDTO = roleModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleModuleDTO);
    }

    /**
     * {@code DELETE  /role-modules/:id} : delete the "id" roleModule.
     *
     * @param id the id of the roleModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-modules/{id}")
    public ResponseEntity<Void> deleteRoleModule(@PathVariable Long id) {
        log.debug("REST request to delete RoleModule : {}", id);
        roleModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
