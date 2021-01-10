package com.eventmate.mapper.implementation;

import com.eventmate.dto.UserDto;
import com.eventmate.entity.User;
import com.eventmate.mapper.ShowcaseMapper;
import com.eventmate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    ShowcaseMapper showcaseMapper;

    @Override
    public User convert(UserDto user) {
        if (user == null) {
            return null;
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setShowcase(showcaseMapper.convert(user.getShowcase()));
       /* newUser.setRoles(user.getAuthorities()
                .stream().map(e-> ((GrantedAuthority) e).getAuthority())).collect(Collectors.to);*/
        return newUser;
    }

    @Override
    @Transactional
    public UserDto convert(User user) {
        if (user == null) {
            return null;
        }
        UserDto newUser = new UserDto();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setAuthorities(user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList()));
        newUser.setShowcase(showcaseMapper.convert(user.getShowcase()));
        newUser.setEventOfferIds(user.getEventOffers()
                .stream().map(e -> e.getId()).collect(Collectors.toSet()));
       /* newUser.setEventOfferResponseIds(user.getEventOfferResponses()
                .stream().map(e -> e.getId()).collect(Collectors.toSet()));*/
        return newUser;
    }
}
