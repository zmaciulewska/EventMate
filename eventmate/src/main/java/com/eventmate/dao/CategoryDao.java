package com.eventmate.dao;

import com.eventmate.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
