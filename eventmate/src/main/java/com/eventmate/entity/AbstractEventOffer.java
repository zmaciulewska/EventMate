package com.eventmate.entity;

import com.eventmate.entity.user.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
abstract class AbstractEventOffer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private Users owner;

    @ManyToOne
    @JoinColumn(name="event_id", nullable = false)
    private Event event;

    private String prefferedGender; //TODO enum

    private Integer prefferedMinAge;

    private Integer prfferedMaxAge;

    private String prefferedLocalization; //TODO google api?

    private LocalDateTime creationDate;

}
