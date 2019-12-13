package com.eventmate.controller.v1;

import com.eventmate.dto.ContactDto;
import com.eventmate.dto.form.ContactFormDto;
import com.eventmate.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/contacts")
@CrossOrigin
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ContactDto getOne(@Valid @PathVariable Long id) {
        return contactService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.ok().build();
    }

    /*@PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ContactDto createOrRetrieveExisting(@Valid @RequestBody ContactDto contactForm, @Valid @PathVariable Long id) {
        return contactService.createOrRetrieveExisting(contactForm);
    }*/

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ContactDto createOrRetrieveExisting(@Valid @RequestBody ContactFormDto contactForm) {
        return contactService.createOrRetrieveExisting(contactForm);
    }
}
