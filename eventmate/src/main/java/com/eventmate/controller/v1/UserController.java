package com.eventmate.controller.v1;

import com.eventmate.dto.*;
import com.eventmate.service.UserService;
import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDto getOne(@Valid @PathVariable Long id) {
        return userService.getOne(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable,
                                                @RequestParam(name = "username", required = false) String username,
                                                @RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.ok(userService.getAll(pageable, username, email));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        userService.delete(id); // todo zrobić soft delete żeby nie kasowalo wydarzeń stworzonych przez uzytkownika
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/showcase")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> update(@Valid @RequestBody ShowcaseDto showcaseDto, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(userService.updateShowcase(showcaseDto, id), HttpStatus.OK);
    }

    @GetMapping("/{id}/events")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Page<EventDto> getUserEvents(@Valid @PathVariable Long id,
                                        Pageable pageable,
                                        @RequestParam(name = "title", required = false) String title,
                                        @RequestParam(name = "localization", required = false) String localization,
                                        @RequestParam(name = "startDate", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                        @RequestParam(name = "endDate", required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                        @RequestParam(name = "categoryCode", required = false) String categoryCode,
                                        @RequestParam(name = "areConfirmed") Boolean areConfirmed
    ) {
        return userService.getUserEvents(id, title, localization, startDate, endDate, categoryCode, pageable);
    }

    @GetMapping("/{id}/event-offers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<EventOfferDto> getUserEventOffers(@Valid @PathVariable Long id) {
        return userService.getUserEventOffers(id);
    }

    @GetMapping("/{id}/showcase")
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ShowcaseDto getUserShowcase(@Valid @PathVariable Long id) {
        return userService.getUserShowcase(id);
    }

    @GetMapping("/{id}/contacts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ContactDto> getUserContacts(@Valid @PathVariable Long id) {
        return userService.getUserContacts(id);
    }
}
