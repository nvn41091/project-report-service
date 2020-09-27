package com.nvn41091.service;

import com.nvn41091.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User saveNoLogin(User user, HttpServletRequest request);

    User saveToLogin(User user);

    void delete(User user);

    Page<User> doSearch(User user, Pageable pageable);

}
