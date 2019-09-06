package com.eventmate.entity;

import com.eventmate.entity.user.Users;
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
    private Users administrator;
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Users reporter;
    private Boolean continous;
    private String siteUrl;
    private LocalDateTime creationDate;
    private LocalDateTime removalDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Price> prices;

    @ManyToMany
    @JoinTable(
            name = "event_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<OneTimeEventOffer> oneTimeEventOffers;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<ContinousEventOffer> continousEventOffers;





}
