package com.nvn41091.service.impl;

import com.nvn41091.domain.User;
import com.nvn41091.repository.UserRepository;
import com.nvn41091.web.rest.errors.BadRequestAlertException;
import com.nvn41091.security.SecurityUtils;
import com.nvn41091.service.UserService;
import com.nvn41091.utils.DataUtil;
import com.nvn41091.utils.Translator;
import io.github.jhipster.security.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Override
    public User saveNoLogin(User user, HttpServletRequest request) {
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        String lang = request.getHeader("Accept-Language").split(",")[0];
        user.setLangKey(lang);
        user.setStatus(true);
        user.setResetKey(RandomUtil.generateResetKey());
        validateEmailAndUsername(user);
        return repository.save(user);
    }

    public void validateEmailAndUsername(User user) {
        if (repository.findAllByUserNameAndIdNotEqual(user.getUserName(), user.getId()).size() > 0) {
            throw new BadRequestAlertException(Translator.toLocale("register.existUsername"), "user", "existUsername");
        }
        if (StringUtils.isNoneEmpty(user.getEmail())) {
            if (repository.findAllByEmailAndIdNotEqual(user.getEmail(), user.getId()).size() > 0) {
                throw new BadRequestAlertException(Translator.toLocale("register.existEmail"), "user", "existEmail");
            }
        }
    }

    @Override
    public User saveToLogin(User user) {
        log.debug("Request to save user : {}", user);
        if (user.getId() == null) {
            // Validate truong hop them moi
            user.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            user.setCreateDate(Timestamp.from(Instant.now()));
        } else {
            // Validate truong hop cap nhat
            List<User> searchExist = repository.findAllById(user.getId());
            if (searchExist.size() == 0) {
                throw new BadRequestAlertException(Translator.toLocale("error.user.notExist"), "user", "user.notExist");
            }
            user.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            user.setLastModifiedDate(Timestamp.from(Instant.now()));
        }
        validateEmailAndUsername(user);
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        if (repository.findAllById(user.getId()).size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("error.user.notExist"), "user", "user.notExist");
        }
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
