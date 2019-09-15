package com.eventmate.mapper;

import com.eventmate.dto.EventDto;
import com.eventmate.entity.Event;

//@Mapper(componentModel = "spring")
public interface EventMapper {

    Event convert(final EventDto event);

    EventDto convert(final Event event);
}
