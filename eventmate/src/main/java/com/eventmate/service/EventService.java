package com.eventmate.service;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.entity.Event;
import com.eventmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService extends AbstractService<EventDto> {
    EventDto create(EventFormDto eventForm);

    EventDto update(EventFormDto eventForm, Long id);

    EventDto confirmPublicEventProposal(Long id);

    List<EventDto> getAllNotConfirmed();

    List<EventDto> getConfirmedOrPrivate();

    List<EventDto> getUserEvents(User user);

    List<Event> getExpiredEvents();

    void softDeleteEvent(Event event);

 //   List<EventDto> getEvents(int page, int limit);
    Page<EventDto> getEvents(Pageable pageable);

    Page<EventDto> getEvents(String title, String localization, LocalDateTime startDate, LocalDateTime endDate, String categoryCode, Pageable pageable);
}
