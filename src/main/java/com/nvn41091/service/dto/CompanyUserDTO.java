package com.nvn41091.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.nvn41091.domain.CompanyUser} entity.
 */
public class CompanyUserDTO implements Serializable {
    
    private Long id;

    private Long userId;

    private Long companyId;

    private Instant updateTime;

    public CompanyUserDTO() {
    }

    public CompanyUserDTO(Long id, Long userId, Long companyId, Instant updateTime) {
        this.id = id;
        this.userId = userId;
        this.companyId = companyId;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyUserDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", companyId=" + getCompanyId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
