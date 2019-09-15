package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
abstract public class AbstractEventOffer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private String prefferedGender; //TODO enum

    private Integer prefferedMinAge;

    private Integer prfferedMaxAge;

    private String prefferedLocalization; //TODO google api?

    private LocalDateTime creationDate;

}
