package com.eventmate.service;

import com.eventmate.dto.EventDto;
import com.eventmate.dto.ShowcaseDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.security.SignUpForm;
import netscape.security.UserTarget;

import java.util.List;

public interface UserService extends AbstractService<UserDto> {
    void signUpUser(SignUpForm signUpRequest);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);
    //Optional<User> findUser(Long id);

    UserDto findUserByEmail(String email);

    UserDto getOneByUsername(String username);

    UserDto updateShowcase(ShowcaseDto showcaseDto, Long id);

    List<EventDto> getUserEvents(Long id);
}
