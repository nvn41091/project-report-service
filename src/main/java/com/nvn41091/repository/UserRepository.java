package com.nvn41091.repository;

import com.nvn41091.domain.User;
import com.nvn41091.service.dto.ResponseJwtDTO;
import com.nvn41091.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserName(String userName);

    List<User> findAllById(Long id);

    @Query(value = "SELECT u from User u WHERE u.userName = :userName AND u.resetKey is not null")
    User findUserLogin(@Param("userName") String userName);

    User findUserByUserNameAndFingerprint(String username, String fingerprint);

    @Query("SELECT u FROM User u INNER JOIN CompanyUser cu on u.id = cu.userId " +
            "WHERE u.userName = :username AND u.fingerprint = :fingerprint AND cu.companyId = :companyId")
    User findUserByUserNameAndFingerprintAndCompanyId(@Param("username") String username, @Param("fingerprint") String fingerprint, @Param("companyId") Long companyId);

    @Query("SELECT u from User u WHERE upper(u.userName) = upper(:userName) ")
    List<User> findAllByUserName(@Param("userName") String userName);

    @Query("SELECT u from User u WHERE upper(u.userName) = upper(:userName) and ( :id is null or u.id <> :id) ")
    List<User> findAllByUserNameAndIdNotEqual(@Param("userName") String userName, @Param("id") Long id);

    @Query("SELECT u from User u WHERE upper(u.email) = upper(:email) and ( :id is null or u.id <> :id) ")
    List<User> findAllByEmailAndIdNotEqual(@Param("email") String email, @Param("id") Long id);

    List<User> findAllByEmail(String email);

    @Query(value = "SELECT DISTINCT u from User u INNER JOIN CompanyUser cu ON u.id = cu.userId " +
            "where 1=1 and (:userName is null or lower(u.userName) like %:userName% ESCAPE '&') " +
            "and (:fullName is null or lower(u.fullName) like %:fullName% ESCAPE '&') " +
            "and (:email is null or lower(u.email) like %:email% escape '&') " +
            "and (:status is null or u.status = :status )" +
            "and cu.companyId = :companyId",
            countQuery = "SELECT count( DISTINCT u) from User u INNER JOIN CompanyUser cu ON u.id = cu.userId " +
                    "where 1=1 and (:userName is null or lower(u.userName) like %:userName% ESCAPE '&') " +
                    "and (:fullName is null or lower(u.fullName) like %:fullName% ESCAPE '&') " +
                    "and (:email is null or lower(u.email) like %:email% escape '&') " +
                    "and (:status is null or u.status = :status ) " +
                    "and cu.companyId = :companyId")
    Page<User> querySearchUser(@Param("userName") String userName,
                               @Param("fullName") String fullName,
                               @Param("email") String email,
                               @Param("status") Boolean status,
                               @Param("companyId") Long companyId,
                               Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE User SET fingerprint = :fingerprint WHERE userName = :username")
    void updateUserByFingerPrint(@Param("fingerprint") String fingerPrint, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE User SET resetKey = :resetKey, resetDate = :resetDate WHERE email = :email ")
    void updateResetKeyByEmail(@Param("resetKey") String resetKey, @Param("email") String email, @Param("resetDate") Timestamp resetDate);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.resetKey = :resetKey AND timestampdiff(minute, u.resetDate, NOW()) < 15")
    User findAllByEmailAndResetKeyAndResetDate(@Param("email") String email, @Param("resetKey") String resetKey);

    List<User> findAllByIdAndResetKey(Long id, String resetKey);

    @Transactional
    @Modifying
    @Query("UPDATE User SET passwordHash = :passwordHash WHERE id = :id ")
    void updatePasswordById(@Param("passwordHash") String passwordHash, @Param("id") Long id);

    @Query("SELECT DISTINCT new com.nvn41091.service.dto.ResponseJwtDTO(c.id, u.userName, c.name) from User u " +
            "INNER JOIN CompanyUser cu on u.id = cu.userId " +
            "INNER JOIN Company c on cu.companyId = c.id WHERE u.userName = :username")
    List<ResponseJwtDTO> getAllCompanyByUserName(@Param("username") String username);
}
