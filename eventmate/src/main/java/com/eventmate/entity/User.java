package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    private LocalDateTime removalDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})  //, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "owner", orphanRemoval = true,
            cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    private Set<EventOffer> eventOffers;

    //@OneToMany(mappedBy = "owner")
    //private Set<EventOfferResponse> eventOfferResponses;

    @OneToMany(mappedBy = "firstPerson", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private Set<Contact> contactsFirstPerson;

    @OneToMany(mappedBy = "secondPerson", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private Set<Contact> contactsSecondPerson;

    @OneToOne(mappedBy = "user", cascade={CascadeType.ALL})
    private Showcase showcase;

}
