package com.eventmate.mapper.implementation;

import com.eventmate.dto.ShowcaseDto;
import com.eventmate.entity.Showcase;
import com.eventmate.mapper.ShowcaseMapper;
import org.springframework.stereotype.Component;

@Component
public class ShowcaseMapperImpl implements ShowcaseMapper {
    @Override
    public ShowcaseDto convert(Showcase entity) {
        if(entity == null) {
            return null;
        }
        ShowcaseDto dto = new ShowcaseDto();
        dto.setBirthDate(entity.getBirthDate());
        dto.setDescription(entity.getDescription());
        dto.setGender(entity.getGender());
        dto.setLocalization(entity.getLocalization());
        dto.setNickname(entity.getNickname());
        dto.setUserId(entity.getUser().getId());
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public Showcase convert(ShowcaseDto dto) {
        if(dto == null) {
            return null;
        }
        return new Showcase();
        //todo dopisac jak bedzie potrzebne
    }
}
