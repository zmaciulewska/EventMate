package com.eventmate.service;

import com.eventmate.dto.UserDto;
import com.eventmate.dto.security.SignUpForm;

public interface UserService extends AbstractService<UserDto> {
    void signUpUser(SignUpForm signUpRequest);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    //Optional<User> findUser(Long id);

    UserDto findUserByEmail(String email);

    UserDto getOneByUsername(String username);
}
