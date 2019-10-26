package com.eventmate.service;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.entity.Event;

public interface EventService extends AbstractService<EventDto> {
    EventDto create(EventFormDto eventForm);

    EventDto update(EventFormDto eventForm, Long id);

    EventDto confirmPublicEventProposal(Long id);

}
