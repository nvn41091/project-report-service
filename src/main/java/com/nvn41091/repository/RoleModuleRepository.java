package com.nvn41091.repository;

import com.nvn41091.domain.RoleModule;

import com.nvn41091.service.dto.TreeViewDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RoleModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModule, Long> {

    @Query(value = "SELECT * FROM ( SELECT m.id, m.name, m.parent_id, NULL AS checked  " +
            "FROM module m  " +
            "WHERE m.STATUS = 1 and id in (SELECT DISTINCT module_id FROM module_action) " +
            "UNION ALL " +
            "SELECT m.id, m.name, m.parent_id, NULL AS checked " +
            "from module m " +
            "WHERE id in (SELECT DISTINCT parent_id from module where id in (SELECT DISTINCT module_id FROM module_action) and status = 1) " +
            "UNION ALL " +
            "SELECT CONCAT('#',m.action_id) as id, a.name, m.module_id as parent_id, " +
            " case " +
            "  when rm.id is null then false " +
            "  else true " +
            " end as checked " +
            "FROM ( SELECT * FROM module_action m  " +
            " WHERE " +
            "  m.module_id IN ( SELECT id FROM module WHERE parent_id IS NOT NULL AND STATUS = 1 )  " +
            " AND m.action_id IN ( SELECT id FROM action WHERE STATUS = 1 )  " +
            " ) m INNER JOIN action a on m.action_id = a.id " +
            "LEFT JOIN (SELECT * FROM role_module where role_id = :id) rm " +
            "on rm.module_id = m.module_id and rm.action_id = m.action_id ) t order by t.name", nativeQuery = true)
    List<Object[]> getAllModuleAndActionByRoleId(@Param("id") Long id);

    List<RoleModule> getAllByRoleId(Long id);
}
