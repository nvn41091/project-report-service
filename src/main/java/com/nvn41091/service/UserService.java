package com.nvn41091.service;

import com.nvn41091.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User save(User user, HttpServletRequest request);

    Page<User> doSearch(User user, Pageable pageable);

}
