/*package com.eventmate.controller.v1;

import com.eventmate.dto.AbstractDto;
import com.eventmate.service.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public abstract class AbstractController<T extends AbstractDto> {

    public abstract AbstractService<T> getService();

    @GetMapping("/{id}")
    public T getOne(@Valid @PathVariable Long id) {
        return getService().getOne(id);
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @PostMapping
    public T create(@Valid @RequestBody T t) {
        return getService().create(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@Valid @RequestBody T t, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(getService().update(t, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        getService().delete(id);
        return ResponseEntity.ok().build();
    }
}*/
