package com.nvn41091.repository;

import com.nvn41091.domain.ProjectInformation;
import com.nvn41091.service.dto.ProjectInformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

/**
 * Spring Data  repository for the ProjectInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectInformationRepository extends JpaRepository<ProjectInformation, Long> {

    @Query(value = "SELECT new com.nvn41091.service.dto.ProjectInformationDTO(c, co.name, ap.value) " +
            "from ProjectInformation c " +
            "left join AppParam ap on c.status = ap.id and ap.status = true " +
            "left join Company co on c.customerId = co.id and co.status = true where 1=1 " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&')" +
            "AND (:startDate is null or c.startDate >= :startDate )" +
            "AND (:actualEndTime is null or c.actualEndTime <= :actualEndTime)" +
            "AND (:endDatePlan is null or c.endDatePlan between :endDatePlanStart and :endDatePlanEnd )" +
            "AND c.companyId = :companyId " +
            "AND (:status is null or c.status = :status)",
            countQuery = "SELECT count(c) from ProjectInformation c " +
                    "left join AppParam ap on c.status = ap.id and ap.status = true " +
                    "left join Company company on c.customerId = company.id and company.status = true where 1=1 " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND (:startDate is null or c.startDate >= :startDate )" +
                    "AND (:actualEndTime is null or c.actualEndTime <= :actualEndTime)" +
                    "AND (:endDatePlan is null or c.endDatePlan between :endDatePlanStart and :endDatePlanEnd )" +
                    "AND c.companyId = :companyId " +
                    "AND (:status is null or c.status = :status)")
    Page<ProjectInformationDTO> doSearch(@Param("name") String name, @Param("code") String code, @Param("status") Long status,
                                         @Param("companyId") Long companyId, @Param("startDate") Instant startDate,
                                         @Param("actualEndTime") Instant actualEndTime, @Param("endDatePlan") Instant endDatePlan,
                                         @Param("endDatePlanStart") Instant endDatePlanStart,
                                         @Param("endDatePlanEnd") Instant endDatePlanEnd,
                                         Pageable pageable);

}
