package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends AbstractDto {
    private String userName;
    private String password;
    private String email;
    private Set<AuthorityDto> authorityList;
    private Set<OneTimeEventOfferDto> oneTimeEventOffers;
    private Set<ContinousEventOfferDto> continousEventOffers;
    private Set<EventOfferResponseDto> eventOfferResponses;
    private ShowcaseDto showcase;
}
