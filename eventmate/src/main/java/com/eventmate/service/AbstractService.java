package com.eventmate.service;

import com.eventmate.dto.AbstractDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AbstractService<T extends AbstractDto> {

    List<T> getAll();

    T getOne(Long id);

    T create(T t);

    ResponseEntity<?> delete(Long id);

    T update(T t);
}
