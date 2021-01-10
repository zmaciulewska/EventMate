package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.EventOfferDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.dto.form.EventOfferFormDto;
import com.eventmate.service.EventOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/event-offers")
public class EventOfferController {

    @Autowired
    private EventOfferService eventOfferService;


    @GetMapping("/{id}")
    public EventOfferDto getOne(@Valid @PathVariable Long id) {
        return eventOfferService.getOne(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EventOfferDto> update(@Valid @RequestBody EventOfferFormDto eventOfferForm, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(eventOfferService.update(eventOfferForm, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        eventOfferService.delete(id);
        return ResponseEntity.ok().build();
    }
}
