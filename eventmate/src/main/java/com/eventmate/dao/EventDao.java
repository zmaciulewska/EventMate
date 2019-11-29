package com.eventmate.dao;

import com.eventmate.entity.Event;
import com.eventmate.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventDao extends AbstractDao<Event> {

    List<Event> findAllByRemovalDateNull();
    List<Event> findAllByRemovalDateNullAndAdministratorNullAndCommon(Boolean common);
    List<Event> findAllByRemovalDateNullAndAdministratorNotNullOrCommonAndRemovalDateNull(Boolean common);
    List<Event> findAllByStartDateLessThanEqualAndStartDateGreaterThanEqual(LocalDateTime endDate, LocalDateTime startDate);
    List<Event> findAllByRemovalDateNullAndReporter(User reporter);
}
