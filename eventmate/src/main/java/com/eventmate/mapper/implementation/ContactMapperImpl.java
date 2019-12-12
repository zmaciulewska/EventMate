package com.eventmate.mapper.implementation;

import com.eventmate.dto.ContactDto;
import com.eventmate.entity.Contact;
import com.eventmate.mapper.ContactMapper;
import com.eventmate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactMapperImpl implements ContactMapper {
    @Autowired
    UserMapper userMapper;

    @Override
    public ContactDto convert(Contact entity) {
        if (entity == null) {
            return null;
        }
        ContactDto dto = new ContactDto();
        dto.setId(entity.getId());
        dto.setCreationDate(entity.getCreationDate());
        dto.setFirstPerson(userMapper.convert(entity.getFirstPerson()));
        dto.setSecondPerson(userMapper.convert(entity.getSecondPerson()));
        return dto;
    }

    @Override
    public Contact convert(ContactDto dto) {
        return null; //todo
    }
}
