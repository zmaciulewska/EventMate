package com.eventmate.controller.v1;

import com.eventmate.dto.CategoryDto;
import com.eventmate.dto.EventDto;
import com.eventmate.dto.EventOfferDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.dto.form.EventOfferFormDto;
import com.eventmate.service.CategoryService;
import com.eventmate.service.EventOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto create(@Valid @RequestBody CategoryDto category) {
        return categoryService.create(category);
    }

    @GetMapping("/{id}")
    public CategoryDto getOne(@Valid @PathVariable Long id) {

        return categoryService.getOne(id);
    }


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto category, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(categoryService.update(category, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
