package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto extends AbstractDto {
    String name;
    String description;
}
