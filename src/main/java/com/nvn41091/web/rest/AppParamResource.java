package com.nvn41091.web.rest;

import com.nvn41091.service.AppParamService;
import com.nvn41091.service.dto.AppParamDTO;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.nvn41091.domain.AppParam}.
 */
@RestController
@RequestMapping("/api/app-param")
public class AppParamResource {

    private final Logger log = LoggerFactory.getLogger(AppParamResource.class);

    private static final String ENTITY_NAME = "appParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppParamService appParamService;

    public AppParamResource(AppParamService appParamService) {
        this.appParamService = appParamService;
    }

    /**
     * {@code POST  /app-params} : Create a new appParam.
     *
     * @param appParamDTO the appParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appParamDTO, or with status {@code 400 (Bad Request)} if the appParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority(\"PARAM#INSERT\")")
    public ResponseEntity<AppParamDTO> createAppParam(@Valid @RequestBody AppParamDTO appParamDTO) throws URISyntaxException {
        log.debug("REST request to save AppParam : {}", appParamDTO);
        if (appParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new appParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppParamDTO result = appParamService.save(appParamDTO);
        return ResponseEntity.created(new URI("/api/app-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-params} : Updates an existing appParam.
     *
     * @param appParamDTO the appParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appParamDTO,
     * or with status {@code 400 (Bad Request)} if the appParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority(\"PARAM#UPDATE\")")
    public ResponseEntity<AppParamDTO> updateAppParam(@Valid @RequestBody AppParamDTO appParamDTO) throws URISyntaxException {
        log.debug("REST request to update AppParam : {}", appParamDTO);
        if (appParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppParamDTO result = appParamService.save(appParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-params} : get all the appParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appParams in body.
     */
    @PostMapping("/doSearch")
    @PreAuthorize("hasAuthority(\"PARAM#SEARCH\")")
    public ResponseEntity<List<AppParamDTO>> getAllAppParams(@RequestBody AppParamDTO appParamDTO, Pageable pageable) {
        log.debug("REST request to get a page of AppParams");
        Page<AppParamDTO> page = appParamService.doSearch(appParamDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /app-params/:id} : delete the "id" appParam.
     *
     * @param id the id of the appParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(\"PARAM#DELETE\")")
    public ResponseEntity<Void> deleteAppParam(@PathVariable Long id) {
        log.debug("REST request to delete AppParam : {}", id);
        appParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/autoCompleteType")
    @PreAuthorize("hasAuthority(\"PARAM#SEARCH\") or hasAuthority(\"PARAM#INSERT\") or hasAuthority(\"PARAM#UPDATE\")")
    public ResponseEntity<List<String>> autoCompleteType(@RequestBody AppParamDTO appParamDTO) {
        List<String> result = appParamService.autoCompleteType(appParamDTO.getType());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/getValueByType")
    public ResponseEntity<List<AppParamDTO>> getValueByType(@RequestBody AppParamDTO appParamDTO) {
        List<AppParamDTO> result = appParamService.getValueByType(appParamDTO);
        return ResponseEntity.ok().body(result);
    }
}
