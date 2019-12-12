package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ShowcaseDto extends AbstractDto {
    private String nickname;
    private String description;
    private String gender;
    private LocalDate birthDate;
    private String localization;
    private Long userId;
    private Integer age;
}
