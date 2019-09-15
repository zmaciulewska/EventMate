package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Contact extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "first_person_id", nullable = false)
    private User firstPerson;

    @ManyToOne
    @JoinColumn(name = "second_person_id", nullable = false)
    private User secondPerson;

    private LocalDateTime creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstPerson, contact.firstPerson) &&
                Objects.equals(secondPerson, contact.secondPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPerson, secondPerson);
    }
}
