package com.nvn41091.service.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public class UserDetailImpl implements Serializable, UserDetails {
    private static final long serialVersionUID = 5926468583005150707L;
    private UserDTO userDTO;

    public UserDetailImpl() {
    }

    public UserDetailImpl(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getUsername() {
        return this.userDTO.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDTO.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.userDTO.getPasswordHash();
    }
}
