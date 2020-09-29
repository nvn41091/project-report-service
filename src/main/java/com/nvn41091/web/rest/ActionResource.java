package com.nvn41091.web.rest;

import com.nvn41091.service.ActionService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.ActionDTO;

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
 * REST controller for managing {@link com.nvn41091.domain.Action}.
 */
@RestController
@RequestMapping("/api/action")
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    private static final String ENTITY_NAME = "action";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionService actionService;

    public ActionResource(ActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * {@code POST  /actions} : Create a new action.
     *
     * @param actionDTO the actionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionDTO, or with status {@code 400 (Bad Request)} if the action has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insert")
    public ResponseEntity<ActionDTO> createAction(@Valid @RequestBody ActionDTO actionDTO) throws URISyntaxException {
        log.debug("REST request to save Action : {}", actionDTO);
        if (actionDTO.getId() != null) {
            throw new BadRequestAlertException("A new action cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionDTO result = actionService.save(actionDTO);
        return ResponseEntity.created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actions} : Updates an existing action.
     *
     * @param actionDTO the actionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionDTO,
     * or with status {@code 400 (Bad Request)} if the actionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<ActionDTO> updateAction(@Valid @RequestBody ActionDTO actionDTO) throws URISyntaxException {
        log.debug("REST request to update Action : {}", actionDTO);
        if (actionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionDTO result = actionService.save(actionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /actions} : get all the actions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actions in body.
     */
    @PostMapping("/doSearch")
    public ResponseEntity<List<ActionDTO>> getAllActions(@RequestBody ActionDTO actionDTO, Pageable pageable) {
        log.debug("REST request to get a page of Actions");
        Page<ActionDTO> page = actionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /actions/:id} : delete the "id" action.
     *
     * @param id the id of the actionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);
        actionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
