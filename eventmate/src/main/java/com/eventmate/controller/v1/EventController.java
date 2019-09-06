package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.service.AbstractService;
import com.eventmate.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController extends AbstractController<EventDto> {

    @Autowired
    private EventService eventService;

    @Override
    public AbstractService<EventDto> getService() {
        return eventService;
    }
}
