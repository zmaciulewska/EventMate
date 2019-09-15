package com.eventmate.mapper.implementation;

import com.eventmate.dao.EventDao;
import com.eventmate.dto.EventDto;
import com.eventmate.entity.Event;
import com.eventmate.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
    @Autowired
    EventDao eventDao;

    @Override
    public Event convert(EventDto event) {
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setDescription(event.getDescription());
        newEvent.setLocalization(event.getLocalization());
        newEvent.setStartDate(event.getStartDate());
        newEvent.setEndDate(event.getEndDate());
        // newEvent.setAdministrator(event.getAdministrator());
        //todo
        return newEvent;
    }

    @Override
    public EventDto convert(Event event) {
        EventDto newEvent = new EventDto();
        newEvent.setTitle(event.getTitle());
        newEvent.setDescription(event.getDescription());
        newEvent.setLocalization(event.getLocalization());
        newEvent.setStartDate(event.getStartDate());
        newEvent.setEndDate(event.getEndDate());
        return newEvent;
    }
}
