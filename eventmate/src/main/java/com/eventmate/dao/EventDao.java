package com.eventmate.dao;

import com.eventmate.entity.Event;
import com.eventmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventDao extends AbstractDao<Event> {

    List<Event> findAllByRemovalDateNull();

    List<Event> findAllByRemovalDateNullAndAdministratorNullAndCommon(Boolean common);

    List<Event> findAllByRemovalDateNullAndAdministratorNotNullOrCommonAndRemovalDateNull(Boolean common);

    List<Event> findAllByStartDateLessThanEqualAndStartDateGreaterThanEqual(LocalDateTime endDate, LocalDateTime startDate);

    List<Event> findAllByRemovalDateNullAndReporter(User reporter);

    List<Event> findAllByRemovalDateNullAndEndDateLessThan(LocalDateTime endDate);

    Page<Event> findAllByRemovalDateNull(Pageable pageableRequest);

    @Query(value = "SELECT e.* FROM event e JOIN event_category ec ON e.id = ec.event_id " +
            "JOIN category c ON ec.category_id = c.id " +
            "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
            "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
            "AND (e.start_date BETWEEN :start_date AND :end_date) " +
            "AND (:category_code='' or c.code = :category_code)" +
            "AND e.removal_date IS NULL",
            countQuery = "SELECT count(*) e FROM event e JOIN event_category ec ON e.id = ec.event_id " +
                    "JOIN category c ON ec.category_id = c.id " +
                    "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
                    "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
                    "AND (e.start_date BETWEEN :start_date AND :end_date) " +
                    "AND (:category_code='' or c.code = :category_code)" +
                    "AND e.removal_date IS NULL",
            nativeQuery = true)
    Page<Event> findEvents(@Param("title") String title,
                           @Param("localization") String localization,
                           @Param("start_date") LocalDateTime startDate,
                           @Param("end_date") LocalDateTime endDate,
                           @Param("category_code") String categoryCode,
                           Pageable pageable);

}
