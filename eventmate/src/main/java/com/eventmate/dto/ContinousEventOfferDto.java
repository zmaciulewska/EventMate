package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContinousEventOfferDto extends AbstractEventOfferDto {
    private LocalDateTime prefferedDate;
}
