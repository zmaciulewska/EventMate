package com.eventmate.service;

import com.eventmate.dto.*;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.entity.Contact;
import com.eventmate.entity.User;
import netscape.security.UserTarget;

import java.util.List;
import java.util.Optional;

public interface UserService extends AbstractService<UserDto> {
    void signUpUser(SignUpForm signUpRequest);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

   // boolean existsUserById(Long id);
    Optional<User> findUser(Long id);

    UserDto findUserByEmail(String email);

    UserDto getOneByUsername(String username);

    UserDto updateShowcase(ShowcaseDto showcaseDto, Long id);

    List<EventDto> getUserEvents(Long id);

    List<EventOfferDto> getUserEventOffers(Long id);

    ShowcaseDto getUserShowcase(Long id);

    List<ContactDto> getUserContacts(Long id);
}
