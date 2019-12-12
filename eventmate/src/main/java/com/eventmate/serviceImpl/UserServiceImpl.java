package com.eventmate.serviceImpl;

import com.eventmate.dao.RoleDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.*;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.entity.Contact;
import com.eventmate.entity.Role;
import com.eventmate.entity.Showcase;
import com.eventmate.entity.User;
import com.eventmate.entity.enumeration.RoleName;
import com.eventmate.error.AppException;
import com.eventmate.error.Error;
import com.eventmate.mapper.ShowcaseMapper;
import com.eventmate.mapper.UserMapper;
import com.eventmate.service.ContactService;
import com.eventmate.service.EventOfferService;
import com.eventmate.service.EventService;
import com.eventmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractServiceImpl<UserDto, User> implements UserDetailsService, UserService {

    private final UserMapper userMapper;

    private final UserDao userDao;

    private final EventService eventService;

    private final EventOfferService eventOfferService;

    private final RoleDao roleDao;

    private final ContactService contactService;

    @Autowired
    ShowcaseMapper showcaseMapper;


    @Autowired
    PasswordEncoder encoder;

    public UserServiceImpl(UserMapper userMapper, UserDao userDao, RoleDao roleDao,
                           EventService eventService, EventOfferService eventOfferService, ContactService contactService) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.roleDao = roleDao;
        this.eventOfferService = eventOfferService;
        this.eventService = eventService;
        this.contactService = contactService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return UserDto.build(user);
    }

    @Override
    public void signUpUser(SignUpForm signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            if (role.equals("admin")) {
                Role adminRole = roleDao.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Admin role not found."));
                roles.add(adminRole);
            } else if (role.equals("user")) {

                Role userRole = roleDao.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("User role not found."));
                roles.add(userRole);
            } else throw new RuntimeException("Role " + role + " not exist.");
        });
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return Optional.empty();
    }

//    @Override
//    public Optional<User> findUser(Long id) {
//        return userDao.findById(id);
//    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userMapper.convert(userDao.findByEmail(email));
    }

    @Override
    public UserDto getOneByUsername(String username) {
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with given username: " + username + " not found."));
        return convert(user);
    }

    @Override
    public UserDto updateShowcase(ShowcaseDto showcaseDto, Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        validateUserAccess(user);
        Showcase showcase = showcaseMapper.convert(showcaseDto);
        showcase.setUser(user);
        user.setShowcase(showcase);
        return convert(userDao.save(user));
    }

    @Override
    public List<EventDto> getUserEvents(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        validateUserAccess(user);

        return eventService.getUserEvents(user);
    }

    @Override
    public List<EventOfferDto> getUserEventOffers(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        validateUserAccess(user);
        return eventOfferService.getUserEventOffers(user);
    }

    @Override
    public ShowcaseDto getUserShowcase(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        return showcaseMapper.convert(user.getShowcase());
    }

    @Override
    public List<ContactDto> getUserContacts(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new AppException(Error.USER_NOT_EXISTS));
        validateUserAccess(user);
        return contactService.getUserContacts(user);
    }

    private void validateUserAccess(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();
        if(!user.getId().equals(principal.getId())
                &&    !principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()))) {
            throw new AppException(Error.USER_NOT_ALLOWED);
        }
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userDao;
    }

    @Override
    public UserDto convert(User entity) {
        return userMapper.convert(entity);
    }

    @Override
    public User convert(UserDto dto) {
        return userMapper.convert(dto);
    }

    @Override
    public List<UserDto> entitiesToDtos(List<User> entities) {
        return entities.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<User> dtosToEntities(List<UserDto> dtos) {
        return dtos.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
