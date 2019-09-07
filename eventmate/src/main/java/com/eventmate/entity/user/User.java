package com.eventmate.entity.user;

import com.eventmate.entity.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User extends AbstractEntity {
    private String userName;
    private String password;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id"))
    private Set<Authority> authorityList;

    @OneToMany(mappedBy = "owner")
    private Set<OneTimeEventOffer> oneTimeEventOffers;

    @OneToMany(mappedBy = "owner")
    private Set<ContinousEventOffer> continousEventOffers;

    @OneToMany(mappedBy = "owner")
    private Set<EventOfferResponse> eventOfferResponses;

    @OneToOne(mappedBy = "user")
    private Showcase showcase;

}
