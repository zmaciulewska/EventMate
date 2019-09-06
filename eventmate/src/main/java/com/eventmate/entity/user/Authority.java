package com.eventmate.entity.user;

import com.eventmate.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
class Authority extends AbstractEntity {

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "authorityList")
    private Set<Users> users;

}
