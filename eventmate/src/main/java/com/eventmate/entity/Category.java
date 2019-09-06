package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
class Category extends AbstractEntity {

    String name;
    String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Event> events;

}
