package com.nvn41091.web.rest;

import com.nvn41091.service.ProjectInformationService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.ProjectInformationDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nvn41091.domain.ProjectInformation}.
 */
@RestController
@RequestMapping("/api")
public class ProjectInformationResource {

    private final Logger log = LoggerFactory.getLogger(ProjectInformationResource.class);

    private static final String ENTITY_NAME = "projectInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectInformationService projectInformationService;

    public ProjectInformationResource(ProjectInformationService projectInformationService) {
        this.projectInformationService = projectInformationService;
    }

    /**
     * {@code POST  /project-informations} : Create a new projectInformation.
     *
     * @param projectInformationDTO the projectInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectInformationDTO, or with status {@code 400 (Bad Request)} if the projectInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-informations")
    public ResponseEntity<ProjectInformationDTO> createProjectInformation(@Valid @RequestBody ProjectInformationDTO projectInformationDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectInformation : {}", projectInformationDTO);
        if (projectInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectInformationDTO result = projectInformationService.save(projectInformationDTO);
        return ResponseEntity.created(new URI("/api/project-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-informations} : Updates an existing projectInformation.
     *
     * @param projectInformationDTO the projectInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectInformationDTO,
     * or with status {@code 400 (Bad Request)} if the projectInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-informations")
    public ResponseEntity<ProjectInformationDTO> updateProjectInformation(@Valid @RequestBody ProjectInformationDTO projectInformationDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectInformation : {}", projectInformationDTO);
        if (projectInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectInformationDTO result = projectInformationService.save(projectInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-informations} : get all the projectInformations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectInformations in body.
     */
    @GetMapping("/project-informations")
    public ResponseEntity<List<ProjectInformationDTO>> getAllProjectInformations(Pageable pageable) {
        log.debug("REST request to get a page of ProjectInformations");
        Page<ProjectInformationDTO> page = projectInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-informations/:id} : get the "id" projectInformation.
     *
     * @param id the id of the projectInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-informations/{id}")
    public ResponseEntity<ProjectInformationDTO> getProjectInformation(@PathVariable Long id) {
        log.debug("REST request to get ProjectInformation : {}", id);
        Optional<ProjectInformationDTO> projectInformationDTO = projectInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectInformationDTO);
    }

    /**
     * {@code DELETE  /project-informations/:id} : delete the "id" projectInformation.
     *
     * @param id the id of the projectInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-informations/{id}")
    public ResponseEntity<Void> deleteProjectInformation(@PathVariable Long id) {
        log.debug("REST request to delete ProjectInformation : {}", id);
        projectInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
