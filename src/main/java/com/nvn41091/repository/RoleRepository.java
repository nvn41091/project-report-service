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

    @Query(value = "SELECT c from Role c " +
            "INNER JOIN CompanyRole cr on c.id = cr.roleId " +
            "where 1=1 " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND cr.companyId = :companyId AND (:userId = :constUserId or c.id <> :constRoleId) " +
            "AND (:status is null or c.status = :status) ",
            countQuery = "SELECT count(c) from Role c " +
                    "INNER JOIN CompanyRole cr on c.id = cr.roleId " +
                    "where 1=1 " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND cr.companyId = :companyId AND (:userId = :constUserId or c.id <> :constRoleId) " +
                    "AND (:status is null or c.status = :status)")
    Page<Role> doSearch(@Param("code") String code, @Param("name") String name, @Param("status") Boolean status,
                        @Param("companyId") Long companyId, @Param("userId") Long userId,
                        @Param("constUserId") Long constUserId, @Param("constRoleId") Long constRoleId, Pageable pageable);

    List<Role> findAllById(Long id);

    @Query("SELECT u from Role u INNER JOIN CompanyRole cr on cr.roleId = u.id and cr.companyId = :companyId WHERE upper(u.code) = upper(:code) AND ( :id is null or u.id <> :id) ")
    List<Role> findAllByCodeAndIdNotEqualAndCompanyId(@Param("code") String code, @Param("id") Long id, @Param("companyId") Long companyId );

    @Query(value = "SELECT r FROM Role r inner join CompanyRole cr on cr.roleId = r.id and cr.companyId = :companyId WHERE r.status = true " +
            "AND (:name is null or lower(r.name) like %:name% ESCAPE '&' or lower(r.code) like %:name% ESCAPE '&' ) " +
            "AND (:companyId = :constCompanyId or (r.id <> :constRoleAdmin )) ")
    List<Role> searchByCodeOrName(@Param("name") String name, @Param("companyId") Long companyId, @Param("constCompanyId") Long constCompanyId, @Param("constRoleAdmin") Long constRoleAdmin);

    @Query(value = "SELECT r FROM Role r " +
            "INNER JOIN CompanyRole cr on r.id = cr.roleId and r.status = true " +
            "INNER JOIN CompanyUser cu on cr.companyId = cu.companyId and cu.userId = :userId and cu.companyId = :companyId ")
    List<Role> getAllRoleByCurrentUser(@Param("userId") Long userId, @Param("companyId") Long companyId);
}
