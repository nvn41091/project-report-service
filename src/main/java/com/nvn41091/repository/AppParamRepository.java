package com.nvn41091.repository;

import com.nvn41091.domain.AppParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AppParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppParamRepository extends JpaRepository<AppParam, Long> {

    @Query(value = "SELECT c from AppParam c where 1=1 " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:type is null or lower(c.type) like %:type% ESCAPE '&') " +
            "AND (:status is null or c.status = :status)",
            countQuery = "SELECT count(c) from AppParam c where 1=1 " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:type is null or lower(c.type) like %:type% ESCAPE '&') " +
                    "AND (:status is null or c.status = :status)")
    Page<AppParam> doSearch(@Param("name") String name,@Param("type") String type, @Param("status") Boolean status, Pageable pageable);

    List<AppParam> findAllById(Long id);

    @Query(value = "SELECT DISTINCT ap.type from AppParam ap WHERE (:type is null or lower(ap.type) like %:type% ESCAPE '&') ")
    List<String> autoCompleteType(@Param("type") String type, Pageable pageable);
}
