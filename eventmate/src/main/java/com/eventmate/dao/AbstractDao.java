package com.eventmate.dao;

import com.eventmate.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractDao<S extends AbstractEntity> extends JpaRepository<S, Long> {
}
