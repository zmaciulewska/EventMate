package com.eventmate.entity;

import com.eventmate.entity.enumeration.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
//todo index on event
public class EventOffer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    private Gender prefferedGender; //TODO enum

    private Integer prefferedMinAge;

    private Integer prefferedMaxAge;

    private String prefferedLocalization; //TODO google api?

    private LocalDateTime creationDate;

    private LocalDateTime removalDate;

   /* @OneToMany(mappedBy = "eventOffer")
    private Set<EventOfferResponse> eventOfferResponses;*/

    private LocalDateTime prefferedDate;

}
