package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
class Price extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    private String name;
    private String description;
    private String price;
    private String currency;
}
