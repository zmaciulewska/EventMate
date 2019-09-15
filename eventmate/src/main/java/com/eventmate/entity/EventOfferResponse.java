package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class EventOfferResponse extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "one_time_event_offer_id", nullable = false)
    private OneTimeEventOffer oneTimeEventOffer;

    @ManyToOne
    @JoinColumn(name = "continous_event_offer_id", nullable = false)
    private ContinousEventOffer continousEventOffer;


}
