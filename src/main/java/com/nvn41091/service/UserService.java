package com.nvn41091.service;

import com.nvn41091.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User save(User user, HttpServletRequest request);

}
