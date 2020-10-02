package com.nvn41091.web.rest;

import com.nvn41091.service.RoleModuleService;
import com.nvn41091.service.dto.TreeViewDTO;
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
@RequestMapping("/api/roleModule")
public class RoleModuleResource {

    private final Logger log = LoggerFactory.getLogger(RoleModuleResource.class);

    private static final String ENTITY_NAME = "roleModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleModuleService roleModuleService;

    public RoleModuleResource(RoleModuleService roleModuleService) {
        this.roleModuleService = roleModuleService;
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<TreeViewDTO>> getAll(@PathVariable Long id) {
        return ResponseEntity.ok().body(roleModuleService.getALl(id));
    }
}
