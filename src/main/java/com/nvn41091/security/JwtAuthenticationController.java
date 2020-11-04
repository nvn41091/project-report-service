package com.nvn41091.security;

import com.nvn41091.domain.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.service.UserRoleService;
import com.nvn41091.service.UserService;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.dto.UserDetailImpl;
import com.nvn41091.utils.JwtTokenUtils;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;

@RestController
@Transactional
@RequestMapping("/api")
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    private static final String ENTITY_NAME = "user";

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user,
                                                       HttpServletRequest request) throws Exception {
        authenticate(user.getUserName(), user.getPasswordHash());;
        final String fingerprint = request.getHeader("fingerprint");
        repository.updateUserByFingerPrint(fingerprint, user.getUserName());
        HttpHeaders headers = new HttpHeaders();
        final String token = jwtTokenUtils.generateToken(SecurityUtils.getCurrentUser().get());
        headers.add("Authorization", token);
        return ResponseEntity.ok().headers(headers).body(Collections.singletonMap("username", user.getUserName()));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User userCreated = userService.saveNoLogin(userDTO, request);
        logger.info(">>>>>>>>> CREATE USER SUCCESS - USER ID: " + userCreated.getId());
        return ResponseEntity.ok().build(); 
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<UserDTO> getUserInfo() {
        UserDTO result = userService.getUserInfo();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<UserDTO> resetPassword(@RequestBody UserDTO userDTO) {
        UserDTO result = userService.resetPassword(userDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/request-email")
    public ResponseEntity<Void> requestEmail(@RequestBody UserDTO userDTO) {
        userService.requestEmail(userDTO);
        return ResponseEntity.noContent().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/request-password")
    public ResponseEntity<?> requestPassword(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User result = userService.requestPassword(userDTO);
        final String token = jwtTokenUtils.generateToken(result);
        final String fingerprint = request.getHeader("fingerprint");
        repository.updateUserByFingerPrint(fingerprint, result.getUserName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return ResponseEntity.ok().headers(headers).body(Collections.singletonMap("username", result.getUserName()));
    }
}
