package com.eventmate.dao;

import com.eventmate.entity.Category;
import com.eventmate.entity.ValueCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends AbstractDao<Category> {

    @Query(value = "SELECT DISTINCT(c.*) FROM category c " +
            "WHERE (:code='' OR UPPER(c.code) LIKE (UPPER(CONCAT('%',:code,'%')))) " +
            "AND (:name='' OR UPPER(c.name) LIKE (UPPER(CONCAT('%',:name,'%')))) ",
            countQuery = "SELECT count( DISTINCT(c.*)) FROM category c " +
                    "WHERE (:code='' OR UPPER(c.code) LIKE (UPPER(CONCAT('%',:code,'%')))) " +
                    "AND (:name='' OR UPPER(c.name) LIKE (UPPER(CONCAT('%',:name,'%')))) ",
            nativeQuery = true)
    Page<Category> getAll(@Param("code") String code,
                          @Param("name")String name,
                          Pageable pageable);

    @Query(value = "SELECT c.name AS label, COUNT(c.*) AS count "
            + "FROM category c JOIN event_category ec ON c.id = ec.category_id " +
            "GROUP BY label ORDER BY label ASC",
            nativeQuery = true)
    List<ValueCount> countCategories();
}
