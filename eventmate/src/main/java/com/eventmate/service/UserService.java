package com.eventmate.service;

import com.eventmate.dto.UserDto;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.entity.User;

import java.util.Optional;

public interface UserService {
    void signUpUser(SignUpForm signUpRequest);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    Optional<User> findUser(Long id);

    UserDto findUserByEmail(String email);
}
