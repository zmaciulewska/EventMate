package com.eventmate.dto.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class CostFormDto {
    @NonNull
    private String name;
    private String description;
    @NonNull
    private double price;
    @NonNull
    private String currency;
}
