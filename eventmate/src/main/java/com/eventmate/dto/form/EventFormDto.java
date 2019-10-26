package com.eventmate.dto.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class EventFormDto {

    @NonNull
    @NotEmpty(message = "Please provide a title")
    private String title;
    @NotEmpty(message = "Please provide a description")
    private String description;
    @NotEmpty(message = "Please provide a localization")
    private String localization;

    //@NotEmpty(message = "Please provide a start date")
    // @DateTimeFormat(iso = DateTimeFormatter.ISO_LOCAL_DATE_TIM
    //@JsonFormat(pattern = "dd.MM.yyyy HH:mm",  timezone="Europe/Zagreb")
    @NonNull
    private LocalDateTime startDate;

    //  @NotEmpty(message = "Please provide a end date")
    //JsonFormat(pattern = "dd.MM.yyyy HH:mm",  timezone="Europe/Zagreb")
    @NonNull
    private LocalDateTime endDate;
    @NonNull
    private Boolean common;
    @NonNull
    private Boolean continous;

    private String siteUrl;
    private Set<CostFormDto> costs;
    private Set<Long> categoryIds;

    public Boolean isCommon() {
        return common;
    }

    public Boolean isContinous() {
        return continous;
    }
}
