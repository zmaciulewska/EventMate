package com.eventmate.serviceImpl;

import com.eventmate.dao.CategoryDao;
import com.eventmate.dto.CategoryDto;
import com.eventmate.dto.EventDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.entity.Category;
import com.eventmate.entity.Event;
import com.eventmate.mapper.CategoryMapper;
import com.eventmate.service.CategoryService;
import com.eventmate.service.EventService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl  extends AbstractServiceImpl<CategoryDto, Category> implements CategoryService {
    private CategoryDao categoryDao;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public JpaRepository<Category, Long> getRepository() {
        return categoryDao;
    }

    @Override
    public CategoryDto convert(Category entity) {
        return categoryMapper.convert(entity);
    }

    @Override
    public Category convert(CategoryDto dto) {
        return categoryMapper.convert(dto);
    }

    @Override
    public List<CategoryDto> entitiesToDtos(List<Category> entities) {
        return entities.stream().map(e -> categoryMapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<Category> dtosToEntities(List<CategoryDto> dtos) {
        return dtos.stream().map(e -> categoryMapper.convert(e)).collect(Collectors.toList());
    }
}


