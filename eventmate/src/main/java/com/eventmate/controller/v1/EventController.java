package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.EventOfferDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.dto.form.EventOfferFormDto;
import com.eventmate.service.EventOfferService;
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
@CrossOrigin
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private EventOfferService eventOfferService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public EventDto create(@Valid @RequestBody EventFormDto eventForm) {
        return eventService.create(eventForm);
    }

    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    // @RequestMapping("/{id}/confirm")
    public  ResponseEntity<EventDto> confirmPublicEventProposal(@Valid @PathVariable Long id, @Valid @RequestBody EventDto event) {
        return new ResponseEntity<>( eventService.confirmPublicEventProposal(id), HttpStatus.OK);

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

    @GetMapping ("/not-confirmed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventDto>> getAllNotConfirmed() {
        return  ResponseEntity.ok(eventService.getAllNotConfirmed());
    }

    @GetMapping ("/confirmed-private")
    public ResponseEntity<List<EventDto>> getConfirmedAndPrivate() {
        return  ResponseEntity.ok(eventService.getConfirmedOrPrivate());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EventDto> update(@Valid @RequestBody EventFormDto eventForm, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(eventService.update(eventForm, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/{id}/event-offers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<EventOfferDto>> getAllEventOffers(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(eventOfferService.getAllEventOffers(id));
    }

    @PostMapping("/{id}/event-offers")
    @PreAuthorize("hasRole('USER')")
    public EventOfferDto create(@Valid @RequestBody EventOfferFormDto eventOfferForm, @Valid @PathVariable Long id) {
        return eventOfferService.create(eventOfferForm, id);
    }


}
