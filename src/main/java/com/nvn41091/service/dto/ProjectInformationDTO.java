package com.nvn41091.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

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

    private Instant endDate;

    private Long money;

    private Long companyContracting;

    private String companyContractingValue;

    private Long companyId;

    @Size(max = 2000)
    private String description;

    private Instant updateTime;

    private Long status;

    private String statusValue;

    public ProjectInformationDTO() {
    }

    public ProjectInformationDTO(Long id, @Size(max = 200) String code, @Size(max = 500) String name, Instant startDate, Instant endDate, Long money, String companyContractingValue, Long companyId, @Size(max = 2000) String description, Instant updateTime, String statusValue) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.money = money;
        this.companyContractingValue = companyContractingValue;
        this.companyId = companyId;
        this.description = description;
        this.updateTime = updateTime;
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

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getCompanyContracting() {
        return companyContracting;
    }

    public void setCompanyContracting(Long companyContracting) {
        this.companyContracting = companyContracting;
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

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getCompanyContractingValue() {
        return companyContractingValue;
    }

    public void setCompanyContractingValue(String companyContractingValue) {
        this.companyContractingValue = companyContractingValue;
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
            ", endDate='" + getEndDate() + "'" +
            ", money=" + getMoney() +
            ", companyContracting=" + getCompanyContracting() +
            ", companyId=" + getCompanyId() +
            ", description='" + getDescription() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
