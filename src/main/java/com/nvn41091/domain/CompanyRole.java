package com.nvn41091.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CompanyRole.
 */
@Entity
@Table(name = "company_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public CompanyRole companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public CompanyRole roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public CompanyRole updateTime(Instant updateTime) {
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
        if (!(o instanceof CompanyRole)) {
            return false;
        }
        return id != null && id.equals(((CompanyRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyRole{" +
            "id=" + getId() +
            ", companyId=" + getCompanyId() +
            ", roleId=" + getRoleId() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
