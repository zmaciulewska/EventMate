package com.eventmate.serviceImpl;

import com.eventmate.dao.RoleDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.entity.Role;
import com.eventmate.entity.enumeration.RoleName;
import com.eventmate.entity.User;
import com.eventmate.mapper.UserMapper;
import com.eventmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserMapper userMapper;

    private final UserDao userDao;

    private final RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    public UserServiceImpl(UserMapper userMapper, UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.roleDao = roleDao;
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
        return userDao.findById(id);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userMapper.convert(userDao.findByEmail(email));
    }
}
