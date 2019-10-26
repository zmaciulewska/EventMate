package com.eventmate.mapper.implementation;

import com.eventmate.dao.EventDao;
import com.eventmate.dto.EventDto;
import com.eventmate.entity.Event;
import com.eventmate.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapperImpl implements EventMapper {
    @Autowired
    EventDao eventDao;

    @Override
    public Event convert(EventDto event) {
        if(event == null) {
            return null;
        }
        Event newEvent = new Event();
        newEvent.setTitle(event.getTitle());
        newEvent.setDescription(event.getDescription());
        newEvent.setLocalization(event.getLocalization());
        newEvent.setStartDate(event.getStartDate());
        newEvent.setEndDate(event.getEndDate());
        newEvent.setCommon(event.isCommon());
        newEvent.setContinous(event.isContinous());
        //todo
        return newEvent;
    }

    @Override
    public EventDto convert(Event event) {
        if(event == null) {
            return null;
        }
        EventDto newEvent = new EventDto();
        newEvent.setId(event.getId());
        newEvent.setTitle(event.getTitle());
        newEvent.setDescription(event.getDescription());
        newEvent.setLocalization(event.getLocalization());
        newEvent.setStartDate(event.getStartDate());
        newEvent.setEndDate(event.getEndDate());
        newEvent.setCommon(event.isCommon());
        newEvent.setAdministratorId(event.getAdministrator() == null ? null : event.getAdministrator().getId() );
        newEvent.setReporterId(event.getReporter() == null ? null : event.getReporter().getId());
        newEvent.setContinous(event.isContinous());
        newEvent.setSiteUrl(event.getSiteUrl());
        newEvent.setCreationDate(event.getCreationDate());
        newEvent.setRemovalDate(event.getRemovalDate());
        newEvent.setCostIds(event.getCosts().stream()
                .map(e -> e.getId()).collect(Collectors.toSet()));
        newEvent.setCategoryIds(event.getCategories().stream()
                .map(e -> e.getId()).collect(Collectors.toSet()));
        return newEvent;
    }
}
