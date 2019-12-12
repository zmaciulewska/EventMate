package com.eventmate.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactDto extends AbstractDto {
    UserDto firstPerson;
    UserDto secondPerson;
    LocalDateTime creationDate;
}
