package com.eventmate.dao;

import com.eventmate.entity.Event;
import com.eventmate.entity.EventOffer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventOfferDao extends AbstractDao<EventOffer> {
     List<EventOffer> findAllByEvent(Event event);
}
