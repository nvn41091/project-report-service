package com.nvn41091.service.dto;

import java.io.Serializable;

public class ResponseJwtDTO implements Serializable {
    private Long companyId;
    private String username;
    private String companyName;
    private String token;

    public ResponseJwtDTO() {
    }

    public ResponseJwtDTO(Long companyId, String username, String companyName) {
        this.companyId = companyId;
        this.username = username;
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
