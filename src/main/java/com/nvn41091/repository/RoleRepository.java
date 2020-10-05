package com.nvn41091.repository;

import com.nvn41091.domain.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT c from Role c where 1=1 " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:status is null or c.status = :status)",
            countQuery = "SELECT count(c) from Role c where 1=1 " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:status is null or c.status = :status)")
    Page<Role> doSearch(@Param("code") String code, @Param("name") String name, @Param("status") Boolean status, Pageable pageable);

    List<Role> findAllById(Long id);

    @Query("SELECT u from Role u WHERE upper(u.code) = upper(:code) AND ( :id is null or u.id <> :id) ")
    List<Role> findAllByCodeAndIdNotEqual(@Param("code") String code, @Param("id") Long id );

    @Query(value = "SELECT r FROM Role r WHERE r.status = true " +
            "AND (:name is null or lower(r.name) like %:name% ESCAPE '&' or lower(r.code) like %:name% ESCAPE '&' ) ")
    List<Role> searchByCodeOrName(@Param("name") String name);

    @Query(value = "SELECT r FROM Role r " +
            "INNER JOIN CompanyRole cr on r.id = cr.roleId and r.status = true " +
            "INNER JOIN CompanyUser cu on cr.companyId = cu.companyId and cu.userId = :userId ")
    List<Role> getAllRoleByCurrentUser(@Param("userId") Long userId);
}
