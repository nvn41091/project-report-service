package com.nvn41091.repository;

import com.nvn41091.domain.ProjectInformation;
import com.nvn41091.service.dto.ProjectInformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectInformationRepository extends JpaRepository<ProjectInformation, Long> {

    @Query(value = "SELECT new com.nvn41091.service.dto.ProjectInformationDTO(c, co.name, ap.value) " +
            "from ProjectInformation c " +
            "left join AppParam ap on c.status = ap.id and ap.status = true " +
            "inner join Company co on c.customerId = co.id and co.status = true where 1=1 " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&')" +
            "AND c.companyId = :companyId " +
            "AND (:status is null or c.status = :status)",
            countQuery = "SELECT count(c) from ProjectInformation c " +
                    "left join AppParam ap on c.status = ap.id and ap.status = true " +
                    "inner join Company company on c.customerId = company.id and company.status = true where 1=1 " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND c.companyId = :companyId " +
                    "AND (:status is null or c.status = :status)")
    Page<ProjectInformationDTO> doSearch(@Param("name") String name, @Param("code") String code, @Param("status") Long status, @Param("companyId") Long companyId, Pageable pageable);

}
