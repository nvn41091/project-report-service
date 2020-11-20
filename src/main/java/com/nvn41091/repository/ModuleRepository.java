package com.nvn41091.repository;

import com.nvn41091.domain.Module;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(value = "SELECT c from Module c where 1=1 " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:status is null or c.status = :status) " +
            "AND (:parentId is null or c.parentId = :parentId)")
    List<Module> doSearch(@Param("code") String code, @Param("name") String name, @Param("status") Boolean status,
                          @Param("parentId") Long id);

    List<Module> findAllById(Long id);

    List<Module> findAllByParentId(Long parentId);

    List<Module> findAllByIdAndStatus(Long id, Boolean status);

    List<Module> findAllByParentIdAndStatus(Long parentId, Boolean status);

    @Query("SELECT u from Module u WHERE upper(u.code) = upper(:code) and (:id is null or u.id <> :id) ")
    List<Module> findAllByCodeAndIdNotContains(@Param("code") String code, @Param("id") Long id);

    @Query("SELECT u from Module u WHERE u.parentId is null and u.pathUrl is null")
    List<Module> findAllParent();

    @Query(value = " SELECT * FROM ( SELECT DISTINCT  " +
            " m.id, m.code, m.name, m.parent_id, m.status, m.path_url, m.icon, m.access_user, m.update_time, m.description " +
            " FROM (select * from user_role WHERE user_id = :id and company_id = :companyId) ur   " +
            "             INNER JOIN (SELECT * FROM role WHERE status = true ) r on ur.role_id = r.id " +
            "             INNER JOIN role_module rm on r.id = rm.role_id     " +
            "             INNER JOIN (SELECT * FROM module WHERE status = true ) m on rm.module_id = m.id " +
            "UNION ALL " +
            " SELECT DISTINCT  " +
            "m.id, m.code, m.name, m.parent_id, m.status, m.path_url, m.icon, m.access_user, m.update_time, m.description  " +
            " FROM module m where id in ( " +
            "  SELECT DISTINCT m.parent_id FROM (select * from user_role WHERE user_id = :id and company_id = :companyId) ur   " +
            "             INNER JOIN (SELECT * FROM role WHERE status = true ) r on ur.role_id = r.id     " +
            "             INNER JOIN role_module rm on r.id = rm.role_id     " +
            "             INNER JOIN (SELECT * FROM module WHERE status = true ) m on rm.module_id = m.id ) ) m ORDER BY m.id, m.name", nativeQuery = true)
    List<Module> findAllMenuByUserId(@Param("id") Long id, @Param("companyId") Long companyId);
}
