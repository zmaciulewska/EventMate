package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User extends AbstractEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "owner")
    private Set<EventOffer> eventOffers;

    @OneToMany(mappedBy = "owner")
    private Set<EventOfferResponse> eventOfferResponses;

    @OneToOne(mappedBy = "user")
    private Showcase showcase;

}
