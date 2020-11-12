package com.nvn41091.repository;

import com.nvn41091.domain.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT c from Company c where 1=1 " +
            "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
            "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
            "AND (:email is null or lower(c.email) like %:email% ESCAPE '&') " +
            "AND (:tel is null or lower(c.tel) like %:tel% ESCAPE '&') " +
            "AND (:status is null or c.status = :status) " +
            "AND c.createBy = :userId ",
            countQuery = "SELECT count(c) from Company c where 1=1 " +
                    "AND (:code is null or lower(c.code) like %:code% ESCAPE '&') " +
                    "AND (:name is null or lower(c.name) like %:name% ESCAPE '&') " +
                    "AND (:email is null or lower(c.email) like %:email% ESCAPE '&') " +
                    "AND (:tel is null or lower(c.tel) like %:tel% ESCAPE '&') " +
                    "AND (:status is null or c.status = :status) " +
                    "AND c.createBy = :userId")
    Page<Company> doSearch(@Param("code") String code, @Param("name") String name,
                           @Param("email") String email, @Param("tel") String tel,
                           @Param("status") Boolean status, @Param("userId") Long userId, Pageable pageable);

    @Query("SELECT u from Company u WHERE upper(u.code) = upper(:code) and ( :id is null or u.id <> :id) ")
    List<Company> findAllByCodeAndIdNotEqual(@Param("code") String code, @Param("id") Long id);

    @Query("SELECT u from Company u WHERE upper(u.email) = upper(:email) and ( :id is null or u.id <> :id) ")
    List<Company> findAllByEmailAndIdNotEqual(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT u from Company u WHERE upper(u.tel) = upper(:tel) and ( :id is null or u.id <> :id) ")
    List<Company> findAllByTelAndIdNotEqual(@Param("tel") String tel, @Param("id") Long id);

    List<Company> findAllById(Long id);

    Company findAllByIdAndCreateBy(Long id, Long createBy);
}
