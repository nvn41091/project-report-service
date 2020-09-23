package com.nvn41091.repository;

import com.nvn41091.model.User;
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

    @Transactional
    @Modifying
    @Query("UPDATE User SET fingerprint = :fingerprint WHERE userName = :username")
    void updateUserByFingerPrint(@Param("fingerprint") String fingerPrint, @Param("username") String username);
}
