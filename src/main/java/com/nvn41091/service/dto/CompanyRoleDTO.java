package com.nvn41091.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.nvn41091.domain.CompanyRole} entity.
 */
public class CompanyRoleDTO implements Serializable {
    
    private Long id;

    private Long companyId;

    private Long roleId;

    private Instant updateTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public CompanyRoleDTO() {
    }

    public CompanyRoleDTO(Long id, Long companyId, Long roleId, Instant updateTime) {
        this.id = id;
        this.companyId = companyId;
        this.roleId = roleId;
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyRoleDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyRoleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyRoleDTO{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", roleId=" + getRoleId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
