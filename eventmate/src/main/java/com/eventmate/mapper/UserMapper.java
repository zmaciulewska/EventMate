package com.eventmate.mapper;

import com.eventmate.dto.UserDto;
import com.eventmate.entity.User;

//@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<UserDto, User> {

    /*User convert(final UserDto user);

    UserDto convert(final User user);*/
}
