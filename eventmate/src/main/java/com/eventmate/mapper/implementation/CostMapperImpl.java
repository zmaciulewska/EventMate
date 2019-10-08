package com.eventmate.mapper.implementation;

import com.eventmate.dto.CostDto;
import com.eventmate.dto.form.CostFormDto;
import com.eventmate.entity.Cost;
import com.eventmate.entity.enumeration.Currency;
import com.eventmate.mapper.CostMapper;
import org.springframework.stereotype.Component;

@Component
public class CostMapperImpl implements CostMapper {
    @Override
    public CostDto convert(Cost entity) {
        if (entity == null ) {
            return null;
        }
        CostDto dto = new CostDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setCurrency(entity.getCurrency().name());
        return dto;
    }

    @Override
    public Cost convert(CostDto dto) {
        if (dto == null ) {
            return null;
        }
        Cost entity = new Cost();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setCurrency(Currency.valueOf(dto.getCurrency()));
        return entity;
    }

    @Override
    public Cost formToEntity(CostFormDto form) {
        if(form == null) {
            return null;
        }
        Cost entity = new Cost();
        entity.setName(form.getName());
        entity.setDescription(form.getDescription());
        entity.setPrice(form.getPrice());
        entity.setCurrency(Currency.valueOf(form.getCurrency()));
        return entity;
    }
}
