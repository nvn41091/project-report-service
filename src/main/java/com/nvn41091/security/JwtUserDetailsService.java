package com.nvn41091.security;

import com.nvn41091.service.dto.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!s.equals("admin")) {
            throw new UsernameNotFoundException(s);
        }
        return new User("admin", "$2a$10$pVxP6FQWd2N76nx2/4v3u.ZGnJYjEBe53jXMjSkC4pbu67awKIH9y");
    }
}
