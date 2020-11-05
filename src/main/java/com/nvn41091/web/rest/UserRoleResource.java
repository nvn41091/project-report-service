package com.nvn41091.web.rest;

import com.nvn41091.service.UserRoleService;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.dto.UserRoleDTO;

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
 * REST controller for managing {@link com.nvn41091.domain.UserRole}.
 */
@RestController
@RequestMapping("/api/userRole")
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private static final String ENTITY_NAME = "userRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRoleService userRoleService;

    public UserRoleResource(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/getByUserId/{id}")
    @PreAuthorize("hasAuthority(\"USER#INSERT\") or hasAuthority(\"USER#UPDATE\")")
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles(@PathVariable Long id) {
        List<UserRoleDTO> lst = userRoleService.getAllByUserIdAndCompanyId(id);
        return ResponseEntity.ok().body(lst);
    }

}
