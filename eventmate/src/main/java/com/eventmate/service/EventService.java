package com.eventmate.service;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.form.EventFormDto;

public interface EventService extends AbstractService<EventDto> {
    EventDto create(EventFormDto eventForm);
    EventDto createEventProposal(EventFormDto eventForm);
    EventDto update(EventFormDto eventForm, Long id);
}
