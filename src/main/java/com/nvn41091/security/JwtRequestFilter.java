package com.nvn41091.security;

import com.nvn41091.domain.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.service.UserRoleService;
import com.nvn41091.service.dto.UserDTO;
import com.nvn41091.service.dto.UserDetailImpl;
import com.nvn41091.service.mapper.UserMapper;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String usernameAndCompanyId = null;
        String jwtToken = null;
        String username = null;
        Long companyId = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                usernameAndCompanyId = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (SignatureException e) {
                logger.error("Invalid Sign");
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
        }
        if (StringUtils.isNoneEmpty(usernameAndCompanyId)) {
            try {
                String[] cut = usernameAndCompanyId.split("\\|");
                companyId = DataUtil.safeToLong(cut[1]);
                username = DataUtil.safeToString(cut[0]);
            } catch (Exception e) {
                username = DataUtil.safeToString(usernameAndCompanyId);
            }
        }
        String fingerprint = request.getHeader("fingerprint");

        // Once we get the token validate it.
        if (StringUtils.isNoneEmpty(usernameAndCompanyId) && StringUtils.isNoneEmpty(fingerprint)) {
            User user;
            if (companyId != null) {
                user = this.repository.findUserByUserNameAndFingerprintAndCompanyId(username, fingerprint, companyId);
            } else {
                user = this.repository.findUserByUserNameAndFingerprint(username, fingerprint);
            }
            if (user != null) {
                UserDTO userDTO = userMapper.toDto(user);
                userDTO.setCompanyId(companyId);
                UserDetailImpl userDetails = new UserDetailImpl(userDTO);
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    // Get Role User
                    List<SimpleGrantedAuthority> lstRole = userRoleService.getRoleByUserId(userDetails.getUserDTO().getId(), companyId);

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
