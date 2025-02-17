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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/moduleAction")
public class ModuleActionResource {

    private final Logger log = LoggerFactory.getLogger(ModuleActionResource.class);

    private static final String ENTITY_NAME = "moduleAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleActionService moduleActionService;

    public ModuleActionResource(ModuleActionService moduleActionService) {
        this.moduleActionService = moduleActionService;
    }


    @GetMapping("/getByModuleId/{id}")
    @PreAuthorize("hasAuthority(\"MODULE#INSERT\") or hasAuthority(\"MODULE#UPDATE\")")
    public ResponseEntity<List<ModuleActionDTO>> getByModuleId(@PathVariable Long id) {
        List<ModuleActionDTO> lst = moduleActionService.getAllByModuleId(id);
        return ResponseEntity.ok().body(lst);
    }
}
