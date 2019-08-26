package com.eventmate.serviceImpl;

import com.eventmate.dao.EventDao;
import com.eventmate.dto.EventDto;
import com.eventmate.entity.Event;
import com.eventmate.mapper.EventMapper;
import com.eventmate.service.EventService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends AbstractServiceImpl<EventDto, Event> implements EventService {

    private EventMapper eventMapper;
    private EventDao eventDao;

    public EventServiceImpl(EventMapper eventMapper, EventDao eventDao) {
        this.eventMapper = eventMapper;
        this.eventDao = eventDao;
    }

    @Override
    public JpaRepository<Event, Long> getRepository() {
        return eventDao;
    }

    @Override
    public EventDto convert(Event entity) {
        return eventMapper.convert(entity);
    }

    @Override
    public Event convert(EventDto dto) {
        return eventMapper.convert(dto);
    }

    @Override
    public List<EventDto> entitiesToDtos(List<Event> entities) {
        return entities.stream().map(e -> eventMapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<Event> dtosToEntities(List<EventDto> dtos) {
        return dtos.stream().map(d -> eventMapper.convert(d)).collect(Collectors.toList());
    }
}
