package com.eventmate.dao;

import com.eventmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends AbstractDao<User> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRemovalDateNull();

    Optional<User> findByIdAndRemovalDateNull(Long id);

    User findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT DISTINCT(u.*) FROM users u " +
            "WHERE (:username='' OR UPPER(u.username) LIKE (UPPER(CONCAT('%',:username,'%')))) " +
            "AND (:email='' OR UPPER(u.email) LIKE (UPPER(CONCAT('%',:email,'%')))) " +
            "AND u.removal_date is null",

            countQuery = "SELECT count( DISTINCT(u.*)) u FROM users u " +
                    "WHERE (:username='' OR UPPER(u.username) LIKE (UPPER(CONCAT('%',:username,'%')))) " +
                    "AND (:email='' OR UPPER(u.email) LIKE (UPPER(CONCAT('%',:email,'%')))) " +
                    "AND u.removal_date is null",

            nativeQuery = true)
    Page<User> findAll(@Param("username") String username,
                       @Param("email") String email,
                       Pageable pageable
    );

}
