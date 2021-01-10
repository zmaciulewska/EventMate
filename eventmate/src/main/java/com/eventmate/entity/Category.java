package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class Category extends AbstractEntity {

    String code;
    String name;
    String description;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    private Set<Event> events;

}
