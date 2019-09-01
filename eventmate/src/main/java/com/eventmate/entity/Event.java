package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Event extends AbstractEntity {

    private String title;
    private String localization;
    private LocalDateTime date;
}
