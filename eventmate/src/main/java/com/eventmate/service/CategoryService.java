package com.eventmate.service;

import com.eventmate.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService extends AbstractService<CategoryDto>  {
    Page<CategoryDto> getAll(Pageable pageable, String code, String name);
}
