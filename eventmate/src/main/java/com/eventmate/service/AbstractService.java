package com.eventmate.service;

import com.eventmate.dto.AbstractDto;

import java.util.List;

public interface AbstractService<T extends AbstractDto> {

    List<T> getAll();

    T getOne(Long id);

    T create(T t);

    void delete(Long id);

    T update(T t, Long id);
}
