package com.nvn41091.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.nvn41091.domain.RoleModule} entity.
 */
public class RoleModuleDTO implements Serializable {

    public RoleModuleDTO() {
    }

    public RoleModuleDTO(String moduleCode, String actionCode) {
        this.moduleCode = moduleCode;
        this.actionCode = actionCode;
    }

    private Long id;

    private Long roleId;

    private Long moduleId;

    private Long actionId;

    private Instant updateTime;

    private String moduleCode;

    private String actionCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleModuleDTO)) {
            return false;
        }

        return id != null && id.equals(((RoleModuleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleModuleDTO{" +
            "id=" + getId() +
            ", roleId=" + getRoleId() +
            ", moduleId=" + getModuleId() +
            ", actionId=" + getActionId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
