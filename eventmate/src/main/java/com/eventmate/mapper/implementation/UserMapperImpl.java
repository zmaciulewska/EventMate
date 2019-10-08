package com.eventmate.mapper.implementation;

import com.eventmate.dto.UserDto;
import com.eventmate.entity.User;
import com.eventmate.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User convert(UserDto user) {
        if(user == null) {
            return null;
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        return newUser;
    }

    @Override
    public UserDto convert(User user) {
        if(user == null) {
            return null;
        }
        UserDto newUser = new UserDto();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        return newUser;
    }
}
