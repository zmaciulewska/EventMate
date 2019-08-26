package com.eventmate.dao;

import com.eventmate.entity.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends AbstractDao<Event> {

   /* @Query(value = "select * from event where ")
    List<Event> findEvent(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
    */
}
