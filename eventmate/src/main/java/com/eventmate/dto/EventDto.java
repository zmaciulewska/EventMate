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
    private UserDto administrator;
    private UserDto reporter;
    private Boolean continous;
    private String siteUrl;
    private LocalDateTime creationDate;
    private LocalDateTime removalDate;
    private Set<CostDto> costs;
    private Set<CategoryDto> categories;
    private Set<OneTimeEventOfferDto> oneTimeEventOffers;
    private Set<ContinousEventOfferDto> continousEventOffers;
}
