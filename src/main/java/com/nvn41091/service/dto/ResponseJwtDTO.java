package com.nvn41091.service.dto;

import java.io.Serializable;

public class ResponseJwtDTO implements Serializable {
    private Long companyId;
    private String companyName;
    private String token;

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
