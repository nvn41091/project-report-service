package com.nvn41091.repository;

import com.nvn41091.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserName(String userName);

    User findUserByUserNameAndFingerprint(String username, String fingerprint);

    List<User> findAllByUserName(String userName);

    List<User> findAllByEmail(String email);

    @Query(value = "SELECT u from User u " +
            "where 1=1 and (:userName is null or lower(u.userName) like %:userName% ESCAPE '&') " +
            "and (:fullName is null or lower(u.fullName) like %:fullName% ESCAPE '&') " +
            "and (:email is null or lower(u.email) like %:email% escape '&') " +
            "and (:status is null or u.status = :status )",
            countQuery = "SELECT count(u) from User u " +
                    "where 1=1 and (:userName is null or lower(u.userName) like %:userName% ESCAPE '&') " +
                    "and (:fullName is null or lower(u.fullName) like %:fullName% ESCAPE '&') " +
                    "and (:email is null or lower(u.email) like %:email% escape '&') " +
                    "and (:status is null or u.status = :status )")
    Page<User> querySearchUser(@Param("userName") String userName,
                               @Param("fullName") String fullName,
                               @Param("email") String email,
                               @Param("status") Boolean status,
                               Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE User SET fingerprint = :fingerprint WHERE userName = :username")
    void updateUserByFingerPrint(@Param("fingerprint") String fingerPrint, @Param("username") String username);
}
