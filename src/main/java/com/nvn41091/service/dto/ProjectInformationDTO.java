package com.nvn41091.service.dto;

import com.nvn41091.domain.ProjectInformation;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.nvn41091.domain.ProjectInformation} entity.
 */
public class ProjectInformationDTO implements Serializable {
    
    private Long id;

    @Size(max = 200)
    private String code;

    @Size(max = 500)
    private String name;

    private Instant startDate;

    private Instant endDatePlan;

    private Instant actualEndTime;

    @Size(max = 500)
    private String money;

    private Long customerId;

    private Long companyId;

    @Size(max = 2000)
    private String description;

    private Long status;

    private Instant updateTime;

    private String customerName;

    private String statusValue;

    private Date start;

    private Date endPlan;

    private Date endTime;

    public ProjectInformationDTO() {
    }

    public ProjectInformationDTO(ProjectInformation projectInformation, String customerName, String statusValue) {
        this.id = projectInformation.getId();
        this.code = projectInformation.getCode();
        this.name = projectInformation.getName();
        this.startDate = projectInformation.getStartDate();
        this.endDatePlan = projectInformation.getEndDatePlan();
        this.actualEndTime = projectInformation.getActualEndTime();
        this.money = projectInformation.getMoney();
        this.customerId = projectInformation.getCustomerId();
        this.companyId = projectInformation.getCompanyId();
        this.description = projectInformation.getDescription();
        this.status = projectInformation.getStatus();
        this.updateTime = projectInformation.getUpdateTime();
        this.customerName = customerName;
        this.statusValue = statusValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDatePlan() {
        return endDatePlan;
    }

    public void setEndDatePlan(Instant endDatePlan) {
        this.endDatePlan = endDatePlan;
    }

    public Instant getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Instant actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEndPlan() {
        return endPlan;
    }

    public void setEndPlan(Date endPlan) {
        this.endPlan = endPlan;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectInformationDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjectInformationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectInformationDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDatePlan='" + getEndDatePlan() + "'" +
            ", actualEndTime='" + getActualEndTime() + "'" +
            ", money='" + getMoney() + "'" +
            ", customerId=" + getCustomerId() +
            ", companyId=" + getCompanyId() +
            ", description='" + getDescription() + "'" +
            ", status=" + getStatus() +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
