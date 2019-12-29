package com.eventmate.service;

import com.eventmate.dto.*;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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

    Page<EventDto> getUserEvents(Long id, String title, String localization, LocalDateTime startDate,
                                 LocalDateTime endDate, String caategoryCode, Pageable pageable);

    List<EventOfferDto> getUserEventOffers(Long id);

    ShowcaseDto getUserShowcase(Long id);

    List<ContactDto> getUserContacts(Long id);
}
