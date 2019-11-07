package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Event extends AbstractEntity {

    private String title;
    private String description;
    private String localization;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean common;
    @ManyToOne
    @JoinColumn(name = "administrator_id")
    private User administrator;
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;
    private Boolean continous;
    private String siteUrl;
    private LocalDateTime creationDate;
    private LocalDateTime removalDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cost> costs;

    @ManyToMany
    @JoinTable(
            name = "event_category",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventOffer> continousEventOffers;

    public Boolean isCommon() {
        return common;
    }

    public Boolean isContinous() {
        return continous;
    }


}
