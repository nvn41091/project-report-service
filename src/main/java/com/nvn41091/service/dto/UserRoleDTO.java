package com.nvn41091.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.nvn41091.domain.UserRole} entity.
 */
public class UserRoleDTO implements Serializable {
    
    private Long id;

    private Long userId;

    private Long roleId;

    private Long companyId;

    private Instant updateTime;

    
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        if (!(o instanceof UserRoleDTO)) {
            return false;
        }

        return id != null && id.equals(((UserRoleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRoleDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", roleId=" + getRoleId() +
            ", companyId=" + getCompanyId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
