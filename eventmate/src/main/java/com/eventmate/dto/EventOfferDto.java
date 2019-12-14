package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventOfferDto extends AbstractDto {

    private LocalDateTime prefferedDate;

    private Long ownerId;

    private Long eventId;

    private String prefferedGender; //TODO enum

    private Integer prefferedMinAge;

    private Integer prfferedMaxAge;

    private String prefferedLocalization; //TODO google api?

    private LocalDateTime creationDate;

    private LocalDateTime removalDate;
}
