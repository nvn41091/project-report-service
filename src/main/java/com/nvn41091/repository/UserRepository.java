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

    User findUserByUserNameAndSessionLoginAndIpLogin(String username, String sessionLogin, String ipLogin);

    @Modifying
    @Transactional
    @Query("UPDATE User SET sessionLogin = :sessionLogin, ipLogin = :ipLogin WHERE userName = :userName")
    void updateUserBySessionLoginAndIpLogin(
            @Param("sessionLogin") String sessionLogin,
            @Param("ipLogin") String ipLogin,
            @Param("userName") String userName);
}
