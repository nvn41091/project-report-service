package com.nvn41091.web.rest;

import com.nvn41091.domain.User;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.service.UserService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "user";

    @Autowired
    private UserService userService;

    @PostMapping("/doSearch")
    public ResponseEntity<List<User>> doSearch(@RequestBody User user, Pageable pageable) {
        log.debug("REST request to search user : {}", user);
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        Page<User> page = userService.doSearch(user, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(\"USER#DELETE\")")
    public ResponseEntity<Void> delete(@RequestBody User user) {
        log.debug("REST request to delete user : {}", user);
        userService.delete(user);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, user.getId().toString())).build();
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority(\"USER#INSERT\")")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to insert user : {}", user);
        if (user.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "id_exists");
        }
        User result = userService.saveToLogin(user);
        return ResponseEntity.created(new URI("/user/insert/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority(\"USER#UPDATE\")")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        log.debug("REST request to update user : {}", user);
        if (user.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id_null");
        }
        User result = userService.saveToLogin(user);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, user.getId().toString()))
                .body(result);
    }
}
