package com.nvn41091.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProjectInformation.
 */
@Entity
@Table(name = "project_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "code", length = 200)
    private String code;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date_plan")
    private Instant endDatePlan;

    @Column(name = "actual_end_time")
    private Instant actualEndTime;

    @Column(name = "money")
    private Long money;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "company_id")
    private Long companyId;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "status")
    private Long status;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ProjectInformation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ProjectInformation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ProjectInformation startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDatePlan() {
        return endDatePlan;
    }

    public ProjectInformation endDatePlan(Instant endDatePlan) {
        this.endDatePlan = endDatePlan;
        return this;
    }

    public void setEndDatePlan(Instant endDatePlan) {
        this.endDatePlan = endDatePlan;
    }

    public Instant getActualEndTime() {
        return actualEndTime;
    }

    public ProjectInformation actualEndTime(Instant actualEndTime) {
        this.actualEndTime = actualEndTime;
        return this;
    }

    public void setActualEndTime(Instant actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Long getMoney() {
        return money;
    }

    public ProjectInformation money(Long money) {
        this.money = money;
        return this;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ProjectInformation customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public ProjectInformation companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public ProjectInformation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public ProjectInformation status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public ProjectInformation updateTime(Instant updateTime) {
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
        if (!(o instanceof ProjectInformation)) {
            return false;
        }
        return id != null && id.equals(((ProjectInformation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectInformation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDatePlan='" + getEndDatePlan() + "'" +
            ", actualEndTime='" + getActualEndTime() + "'" +
            ", money=" + getMoney() +
            ", customerId=" + getCustomerId() +
            ", companyId=" + getCompanyId() +
            ", description='" + getDescription() + "'" +
            ", status=" + getStatus() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
