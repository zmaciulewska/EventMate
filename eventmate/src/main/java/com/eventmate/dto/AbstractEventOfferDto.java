package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractEventOfferDto extends AbstractDto {

    private UserDto owner;

    private EventDto event;

    private String prefferedGender; //TODO enum

    private Integer prefferedMinAge;

    private Integer prfferedMaxAge;

    private String prefferedLocalization; //TODO google api?

    private LocalDateTime creationDate;
}
