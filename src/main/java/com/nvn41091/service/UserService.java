package com.nvn41091.service;

import com.nvn41091.domain.User;
import com.nvn41091.service.dto.ResponseJwtDTO;
import com.nvn41091.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User saveNoLogin(UserDTO user, HttpServletRequest request);

    User saveToLogin(UserDTO user);

    void delete(User user);

    Page<User> doSearch(User user, Pageable pageable);

    UserDTO getUserInfo();

    void requestEmail(UserDTO userDTO);

    User requestPassword(UserDTO userDTO);

    UserDTO resetPassword(UserDTO userDTO);

    List<ResponseJwtDTO> createToken(UserDTO userDTO);
}
