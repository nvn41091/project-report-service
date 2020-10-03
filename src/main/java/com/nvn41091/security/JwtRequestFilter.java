package com.nvn41091.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nvn41091.domain.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.repository.UserRoleRepository;
import com.nvn41091.service.UserRoleService;
import com.nvn41091.service.dto.RoleModuleDTO;
import com.nvn41091.service.dto.UserDetailImpl;
import com.nvn41091.service.dto.UserRoleDTO;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.JwtTokenUtils;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (SignatureException e) {
                logger.error("Invalid Sign");
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
        }
        String fingerprint = request.getHeader("fingerprint");

        // Once we get the token validate it.
        if (StringUtils.isNoneEmpty(username) && StringUtils.isNoneEmpty(fingerprint)) {
            User user = this.repository.findUserByUserNameAndFingerprint(username, fingerprint);
            if (user != null) {
                UserDetailImpl userDetails = new UserDetailImpl(user);
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    // Get Role User
                    List<SimpleGrantedAuthority> lstRole = userRoleService.getRoleByUserId(userDetails.getUser().getId());

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, lstRole);
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
