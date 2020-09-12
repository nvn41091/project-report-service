package com.nvn41091.security;

import com.nvn41091.model.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user,
                                                         HttpServletRequest request) throws Exception {
        authenticate(user.getUserName(), user.getPasswordHash());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUserName());
        final String token = jwtTokenUtils.generateToken(userDetails);
        final String fingerprint = request.getHeader("fingerprint");
        repository.updateUserByFingerPrint(fingerprint, user.getUserName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Access-Control-Expose-Headers", "Authorization");
        return ResponseEntity.ok().headers(headers).body(Collections.singletonMap("username", user.getUserName()));
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
}
