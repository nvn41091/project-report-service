package com.nvn41091.security;

import com.nvn41091.model.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.service.dto.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findUserLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserDetailImpl(user);
    }
}
