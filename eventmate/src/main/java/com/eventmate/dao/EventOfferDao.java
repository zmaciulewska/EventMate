package com.eventmate.dao;

import com.eventmate.entity.Event;
import com.eventmate.entity.EventOffer;
import com.eventmate.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventOfferDao extends AbstractDao<EventOffer> {
     List<EventOffer> findAllByEvent(Event event);

     List<EventOffer>  findAllByOwnerAndRemovalDateNull(User owner);

     @Modifying
     @Query("DELETE FROM EventOffer e WHERE e.id = ?1")
     @Transactional
     void delete(Long id);

}