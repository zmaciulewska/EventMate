package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.service.AbstractService;
import com.eventmate.service.EventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController extends AbstractController<EventDto> {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public AbstractService<EventDto> getService() {
        return eventService;
    }
}
