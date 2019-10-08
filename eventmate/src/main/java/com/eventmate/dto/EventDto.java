package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class EventDto extends AbstractDto {

    private String title;
    private String description;
    private String localization;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean common;
    private Long administratorId;
    private Long reporterId;
    private Boolean continous;
    private String siteUrl;
    private LocalDateTime creationDate;
    private LocalDateTime removalDate;
    private Set<Long> costIds;
    private Set<Long> categoryIds;
    // private Set<Long> oneTimeEventOfferIds;
    // private Set<Long> continousEventOfferIds;

    public Boolean isCommon() {
        return common;
    }

    public Boolean isContinous() {
        return continous;
    }
}
