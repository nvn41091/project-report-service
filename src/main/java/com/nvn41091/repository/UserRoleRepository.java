package com.nvn41091.repository;

import com.nvn41091.domain.Module;
import com.nvn41091.domain.UserRole;

import com.nvn41091.service.dto.UserRoleDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> getAllByUserId(Long id);

    @Query(value = "select m.code moduleCode, a.code actionCode from  " +
            " (select * from role WHERE id in (select role_id from user_role where user_id = :id) and status = 1 ) ur " +
            " inner join role_module rm on ur.id = rm.role_id  " +
            " left join ( select * from module WHERE status = 1 ) m on rm.module_id = m.id " +
            " left join ( select * from action WHERE status = 1 ) a on rm.action_id = a.id", nativeQuery = true)
    List<Object[]> getUserRole(@Param("id") Long id);

    void deleteAllByUserId(Long id);
}
