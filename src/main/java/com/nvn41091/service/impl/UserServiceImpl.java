package com.nvn41091.service.impl;

import com.nvn41091.model.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.rest.errors.BadRequestAlertException;
import com.nvn41091.service.UserService;
import com.nvn41091.utils.Translator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user, HttpServletRequest request) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        String lang = request.getHeader("Accept-Language").split(",")[0];
        user.setLangKey(lang);
        user.setStatus(true);
        if (StringUtils.isNoneEmpty(user.getEmail())) {
            if (repository.findAllByEmail(user.getEmail()).size() > 0) {
                throw new BadRequestAlertException(Translator.toLocale("register.existUsername"), "register.existUsername", "register.existUsername");
            }
        }
        return repository.save(user);
    }
}
