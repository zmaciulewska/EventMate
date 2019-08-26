package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto extends AbstractDto {

    private String title;
    private String localization;
    private LocalDateTime date;
}
