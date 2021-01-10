package com.eventmate.dao;

import com.eventmate.entity.Event;
import com.eventmate.entity.User;
import com.eventmate.entity.ValueCount;
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

    List<Event> findAllByReporter(User reporter);

    List<Event> findAllByRemovalDateNullAndAdministratorNullAndCommon(Boolean common);

    List<Event> findAllByRemovalDateNullAndAdministratorNotNullOrCommonAndRemovalDateNull(Boolean common);

    List<Event> findAllByStartDateLessThanEqualAndStartDateGreaterThanEqual(LocalDateTime endDate, LocalDateTime startDate);


    @Query(value = "SELECT DISTINCT(e.*) FROM event e LEFT OUTER JOIN event_category ec ON e.id = ec.event_id " +
            " LEFT OUTER JOIN category c ON ec.category_id = c.id " +
            "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
            "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
            "AND (e.start_date BETWEEN :start_date AND :end_date) " +
            "AND (:category_code='' or c.code = :category_code)" +
            "AND e.removal_date IS NULL " +
            "AND (e.reporter_id = :reporter_id)",
            countQuery = "SELECT count( DISTINCT(e.*)) e FROM event e LEFT OUTER JOIN event_category ec ON e.id = ec.event_id " +
                    " LEFT OUTER JOIN category c ON ec.category_id = c.id " +
                    "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
                    "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
                    "AND (e.start_date BETWEEN :start_date AND :end_date) " +
                    "AND (:category_code='' or c.code = :category_code)" +
                    "AND e.removal_date IS NULL " +
                    "AND (e.reporter_id = :reporter_id)",
            nativeQuery = true)
    Page<Event> findUserEvents(@Param("reporter_id") Long reporterId,
                               @Param("title") String title,
                               @Param("localization") String localization,
                               @Param("start_date") LocalDateTime startDate,
                               @Param("end_date") LocalDateTime endDate,
                               @Param("category_code") String categoryCode,
                               Pageable pageable
                               );

    List<Event> findAllByRemovalDateNullAndEndDateLessThan(LocalDateTime endDate);

    Page<Event> findAllByRemovalDateNull(Pageable pageableRequest);

    @Query(value ="SELECT DISTINCT(e.*) FROM event e LEFT OUTER JOIN event_category ec ON e.id = ec.event_id " +
            " LEFT OUTER JOIN category c ON ec.category_id = c.id " +
            "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
            "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
            "AND (e.start_date BETWEEN :start_date AND :end_date) " +
            "AND (:category_code='' or c.code = :category_code)" +
            "AND e.removal_date IS NULL " +
            "AND e.administrator_id IS NULL AND e.common = 'yes' ",
            countQuery = "SELECT count(*) e FROM event e JOIN event_category ec ON e.id = ec.event_id " +
                    "JOIN category c ON ec.category_id = c.id " +
                    "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
                    "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
                    "AND (e.start_date BETWEEN :start_date AND :end_date) " +
                    "AND (:category_code='' or c.code = :category_code)" +
                    "AND e.removal_date IS NULL " +
                    "AND e.administrator_id IS NULL AND e.common = 'yes' ",
            nativeQuery = true)
    Page<Event> findNotConfirmedEvents(@Param("title") String title,
                           @Param("localization") String localization,
                           @Param("start_date") LocalDateTime startDate,
                           @Param("end_date") LocalDateTime endDate,
                           @Param("category_code") String categoryCode,
                           Pageable pageable);

    @Query(value = "SELECT DISTINCT(e.*) FROM event e LEFT OUTER JOIN event_category ec ON e.id = ec.event_id " +
            " LEFT OUTER JOIN category c ON ec.category_id = c.id " +
            "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
            "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
            "AND (e.start_date BETWEEN :start_date AND :end_date) " +
            "AND (:category_code='' or c.code = :category_code)" +
            "AND e.removal_date IS NULL " +
            "AND (administrator_id IS NOT NULL OR e.common='false' )",
            countQuery = "SELECT count( DISTINCT(e.*)) e FROM event e LEFT OUTER JOIN event_category ec ON e.id = ec.event_id " +
                    " LEFT OUTER JOIN category c ON ec.category_id = c.id " +
                    "WHERE (:title='' OR UPPER(e.title) LIKE (UPPER(CONCAT('%',:title,'%')))) " +
                    "AND (:localization='' OR UPPER(e.localization) LIKE (UPPER(CONCAT('%',:localization,'%')))) " +
                    "AND (e.start_date BETWEEN :start_date AND :end_date) " +
                    "AND (:category_code='' or c.code = :category_code)" +
                    "AND e.removal_date IS NULL " +
                    "AND (administrator_id IS NOT NULL OR e.common='false' )",
            nativeQuery = true)
    Page<Event> findEvents(@Param("title") String title,
                                       @Param("localization") String localization,
                                       @Param("start_date") LocalDateTime startDate,
                                       @Param("end_date") LocalDateTime endDate,
                                       @Param("category_code") String categoryCode,
                                       Pageable pageable);





  /*  SELECT date_trunc('month', txn_date) AS txn_month, sum(amount) as monthly_sum
    FROM yourtable
    GROUP BY txn_month

*/
    @Query(value = "SELECT date_trunc('month', e.start_date) AS label, COUNT(e.*) AS count "
            + "FROM event AS e " +
            "GROUP BY label ORDER BY label ASC",
            nativeQuery = true)
    List<ValueCount> countEventsByMonth();
}
