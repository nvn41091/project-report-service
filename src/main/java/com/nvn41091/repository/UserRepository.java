package com.nvn41091.repository;

import com.nvn41091.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserName(String userName);

    User findUserByUserNameAndSessionLoginAndIpLoginAndMacLogin(String username, String sessionLogin, String ipLogin, String macLogin);

    @Modifying
    @Transactional
    @Query("UPDATE User SET sessionLogin = :sessionLogin, ipLogin = :ipLogin, macLogin = :macLogin WHERE userName = :userName")
    void updateUserBySessionLoginAndIpLoginAndMacLogin(
            @Param("sessionLogin") String sessionLogin,
            @Param("ipLogin") String ipLogin,
            @Param("macLogin") String macLogin,
            @Param("userName") String userName);
}
