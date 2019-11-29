package com.eventmate.controller.v1;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.ShowcaseDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
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
    public List<EventDto> getUserEvents(@Valid @PathVariable Long id) {
        return userService.getUserEvents(id);
    }



}
