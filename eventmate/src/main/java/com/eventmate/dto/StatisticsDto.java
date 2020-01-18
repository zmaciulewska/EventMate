package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticsDto {
    List<String> labels;
    List<Integer> values;
    List<Long> percentage;
}
