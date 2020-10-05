package com.nvn41091.web.rest;

import com.nvn41091.service.CompanyRoleService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.CompanyRoleDTO;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nvn41091.domain.CompanyRole}.
 */
@RestController
@RequestMapping("/api/companyRole")
public class CompanyRoleResource {

    private final Logger log = LoggerFactory.getLogger(CompanyRoleResource.class);

    private static final String ENTITY_NAME = "companyRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyRoleService companyRoleService;

    public CompanyRoleResource(CompanyRoleService companyRoleService) {
        this.companyRoleService = companyRoleService;
    }

    @GetMapping("/getAllByCompanyId/{id}")
    @PreAuthorize("hasAuthority(\"COMPANY#ROLE\")")
    public ResponseEntity<List<CompanyRoleDTO>> getCompanyRole(@PathVariable Long id) {
        log.debug("REST request to get all role : {}", id);
        List<CompanyRoleDTO> lst = companyRoleService.getAllByCompanyId(id);
        return ResponseEntity.ok().body(lst);
    }

    @PostMapping("/save/{companyId}")
    @PreAuthorize("hasAuthority(\"COMPANY#ROLE\")")
    public ResponseEntity<List<CompanyRoleDTO>> save(@RequestBody List<CompanyRoleDTO> lst, @PathVariable Long companyId) throws URISyntaxException {
        log.debug("REST to save by companyId");
        List<CompanyRoleDTO> result = companyRoleService.saveAll(lst, companyId);
        return ResponseEntity.created(new URI("/api/companyRole/save/" + companyId))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(companyId)))
                .body(result);
    }
}
