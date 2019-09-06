package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class ContinousEventOffer extends AbstractEventOffer {
    private LocalDateTime prefferedDate;

    @OneToMany(mappedBy = "continousEventOffer")
    private Set<EventOfferResponse> eventOfferResponses;
}
