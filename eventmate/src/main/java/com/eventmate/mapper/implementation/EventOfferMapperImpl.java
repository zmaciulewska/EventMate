package com.eventmate.mapper.implementation;

import com.eventmate.dto.EventOfferDto;
import com.eventmate.entity.EventOffer;
import com.eventmate.mapper.EventOfferMapper;
import org.springframework.stereotype.Component;

@Component
public class EventOfferMapperImpl implements EventOfferMapper {
    @Override
    public EventOfferDto convert(EventOffer entity) {
        if(entity == null) {
            return null;
        }
        EventOfferDto dto = new EventOfferDto();
        dto.setCreationDate(entity.getCreationDate());
        dto.setEventId(entity.getEvent().getId());
        dto.setOwnerId(entity.getOwner().getId());
        dto.setPrefferedDate(entity.getPrefferedDate());
        dto.setPrefferedLocalization(entity.getPrefferedLocalization());
        dto.setPrefferedGender(entity.getPrefferedGender().name());
        dto.setPrefferedMinAge(entity.getPrefferedMinAge());
        dto.setPrfferedMaxAge(entity.getPrefferedMaxAge());
        dto.setId(entity.getId());
        return  dto;
     }

    @Override
    public EventOffer convert(EventOfferDto dto) {
        //todo
        return null;
    }
}
