package com.eventmate.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventOfferFormDto {

    private LocalDateTime prefferedDate;

    private String prefferedGender;

    private Integer prefferedMinAge;

    private Integer prefferedMaxAge;

    private String prefferedLocalization; // not used


}
