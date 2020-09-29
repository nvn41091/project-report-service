package com.nvn41091.repository;

import com.nvn41091.domain.Action;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {


    @Query(value = "SELECT c from Action c where 1=1 " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:status is null or c.status = :status)",
            countQuery = "SELECT count(c) from Action c where 1=1 " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:status is null or c.status = :status)")
    Page<Action> doSearch(@Param("code") String code, @Param("name") String name, @Param("status") Boolean status, Pageable pageable);

    @Query("SELECT u from Action u WHERE upper(u.code) = upper(:code) and ( :id is null or u.id != :id) ")
    List<Action> findAllByCodeAndIdNotEqual(@Param("code") String code, @Param("id") Long id);

    List<Action> findAllById(Long id);
}
