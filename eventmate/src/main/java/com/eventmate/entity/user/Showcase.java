package com.eventmate.entity.user;

import com.eventmate.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
class Showcase extends AbstractEntity {
    private String nickname;
    private String description;
    private String gender;
    private LocalDate birthDate;
    private String localization;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

}
