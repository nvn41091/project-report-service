package com.nvn41091.service.impl;

import com.nvn41091.model.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.rest.errors.BadRequestAlertException;
import com.nvn41091.service.UserService;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
@Transactional
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
                throw new BadRequestAlertException(Translator.toLocale("register.existEmail"), "user", "existEmail");
            }
        }
        if (repository.findAllByUserName(user.getUserName()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("register.existUsername"), "user", "existUsername");
        }
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        this.repository.delete(user);
    }

    @Override
    public Page<User> doSearch(User user, Pageable pageable) {
        Page<User> rs = repository.querySearchUser(DataUtil.makeLikeParam(user.getUserName()),
                DataUtil.makeLikeParam(user.getFullName()),
                DataUtil.makeLikeParam(user.getEmail()),
                user.getStatus(),
                pageable);
        return new PageImpl<>(rs.getContent(), pageable, rs.getTotalElements());
    }
}
