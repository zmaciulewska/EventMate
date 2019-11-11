package com.eventmate.controller.v1;

import com.eventmate.dto.CostDto;
import com.eventmate.service.CostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/costs")
@CrossOrigin(origins = "http://localhost:4200")
public class CostController {
    private static final Logger logger = LoggerFactory.getLogger(CostController.class);

    @Autowired
    private CostService costService;

    /*@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto create(@Valid @RequestBody CategoryDto category) {
        return categoryService.create(category);
    }*/

    @GetMapping("/{id}")
    public CostDto getOne(@Valid @PathVariable Long id) {

        return costService.getOne(id);
    }


    @GetMapping
    public ResponseEntity<List<CostDto>> getAll() {
        return ResponseEntity.ok(costService.getAll());
    }

/*    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto category, @Valid @PathVariable Long id) {
        return new ResponseEntity<>(categoryService.update(category, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }*/

}
