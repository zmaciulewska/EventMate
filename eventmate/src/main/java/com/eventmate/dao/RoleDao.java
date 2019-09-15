package com.eventmate.dao;

import com.eventmate.entity.Role;
import com.eventmate.entity.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends AbstractDao<Role> {
    Optional<Role> findByName(RoleName roleName);
}
