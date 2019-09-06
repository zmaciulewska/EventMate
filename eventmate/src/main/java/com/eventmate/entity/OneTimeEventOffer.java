package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
public class OneTimeEventOffer extends AbstractEventOffer {

    @OneToMany(mappedBy = "oneTimeEventOffer")
    private Set<EventOfferResponse> eventOfferResponses;

}
