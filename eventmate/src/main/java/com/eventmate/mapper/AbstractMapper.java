package com.eventmate.mapper;

import com.eventmate.dto.AbstractDto;
import com.eventmate.entity.AbstractEntity;

public interface AbstractMapper<T extends AbstractDto, S extends AbstractEntity> {
    abstract T convert(S entity);
    abstract S convert(T dto);
}
