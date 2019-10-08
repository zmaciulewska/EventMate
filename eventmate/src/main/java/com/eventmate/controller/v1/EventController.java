package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EventDto create(@Valid @RequestBody EventFormDto eventForm) {
        return eventService.create(eventForm);
    }

    @PostMapping
    @RequestMapping("/proposal")
    @PreAuthorize("hasRole('USER')")
    public EventDto createEventProposal(@Valid @RequestBody EventFormDto eventForm) {
        return eventService.createEventProposal(eventForm);

    }

    @GetMapping("/{id}")
    public EventDto getOne(@Valid @PathVariable Long id) {
        logger.warn(String.valueOf(LocalDateTime.now()));
        return eventService.getOne(id);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@Valid @RequestBody EventFormDto eventForm, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(eventService.update(eventForm, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }


}
