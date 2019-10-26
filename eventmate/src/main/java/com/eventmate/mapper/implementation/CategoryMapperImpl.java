package com.eventmate.mapper.implementation;

import com.eventmate.dto.CategoryDto;
import com.eventmate.entity.Category;
import com.eventmate.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto convert(Category entity) {
        if (entity == null) {
            return null;
        }
        CategoryDto category = new CategoryDto();
        category.setId(entity.getId());
        category.setCode(entity.getCode());
        category.setDescription(entity.getDescription());
        category.setName(entity.getName());
        return category;
    }

    @Override
    public Category convert(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setCode(dto.getCode());
        category.setDescription(dto.getDescription());
        category.setName(dto.getName());
        return category;
    }
}
