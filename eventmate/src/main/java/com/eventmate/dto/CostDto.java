package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostDto extends AbstractDto {

    private String name;
    private String description;
    private Double price;
    private String currency;
}