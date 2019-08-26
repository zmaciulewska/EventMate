package com.eventmate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Event extends AbstractEntity {

    private String title;
    private String localization;
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        if (!super.equals(o)) return false;
        Event that = (Event) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(localization, that.localization) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, localization, date);
    }
}
