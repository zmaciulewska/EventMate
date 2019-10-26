package com.eventmate.entity;

import com.eventmate.entity.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Cost extends AbstractEntity {

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
    private String name;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Cost() {
    }

}
