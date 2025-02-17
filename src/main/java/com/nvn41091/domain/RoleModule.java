package com.nvn41091.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A RoleModule.
 */
@Entity
@Table(name = "role_module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "action_id")
    private Long actionId;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public RoleModule roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public RoleModule moduleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getActionId() {
        return actionId;
    }

    public RoleModule actionId(Long actionId) {
        this.actionId = actionId;
        return this;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public RoleModule updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleModule)) {
            return false;
        }
        return id != null && id.equals(((RoleModule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleModule{" +
            "id=" + getId() +
            ", roleId=" + getRoleId() +
            ", moduleId=" + getModuleId() +
            ", actionId=" + getActionId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
