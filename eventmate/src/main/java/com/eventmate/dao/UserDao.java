package com.eventmate.dao;

import com.eventmate.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends AbstractDao<User> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
