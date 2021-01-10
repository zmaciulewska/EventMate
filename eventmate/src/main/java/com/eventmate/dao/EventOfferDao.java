package com.eventmate.dao;

import com.eventmate.entity.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventOfferDao extends AbstractDao<EventOffer> {
    List<EventOffer> findAllByEvent(Event event);

    List<EventOffer> findAllByOwnerAndRemovalDateNull(User owner);

    Optional<EventOffer> findByIdAndRemovalDateNull(Long id);

    @Modifying
    @Query("DELETE FROM EventOffer e WHERE e.id = ?1")
    @Transactional
    void delete(Long id);

    @Query(value = "SELECT eo.preffered_min_age AS min, eo.preffered_max_age AS max, COUNT(eo.*) AS count "
            + "FROM event_offer eo " +
            "GROUP BY min, max ",
            nativeQuery = true)
    List<MinMaxCount> getMinMaxAgeCount();

    @Query(value = "SELECT eo.preffered_gender AS label, COUNT(eo.*) AS count "
            + "FROM event_offer eo " +
            "GROUP BY label",
            nativeQuery = true)
    List<ValueCount> getGenderCount();

    @Query(value = "SELECT min(eo.preffered_min_age)"
            + "FROM event_offer eo ",
            nativeQuery = true)
    Integer getMinAge();

    @Query(value = "SELECT max(eo.preffered_min_age)"
            + "FROM event_offer eo ",
            nativeQuery = true)
    Integer getMaxAge();

}