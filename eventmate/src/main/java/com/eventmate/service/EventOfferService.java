package com.eventmate.service;

import com.eventmate.dto.EventOfferDto;
import com.eventmate.dto.form.EventOfferFormDto;

import java.util.List;

public interface EventOfferService extends AbstractService<EventOfferDto> {
    List<EventOfferDto> getAllEventOffers(Long eventId);

    EventOfferDto create(EventOfferFormDto eventOfferForm, Long eventId);
    EventOfferDto update(EventOfferFormDto eventOfferForm, Long id);
}
