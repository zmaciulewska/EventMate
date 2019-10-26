package com.eventmate.controller.v1;

import com.eventmate.configuration.JwtProvider;
import com.eventmate.dto.UserDto;
import com.eventmate.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sample")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();
        logger.info("USER: " + principal.getUsername());
        return "hello admin.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        return "hello user.";
    }


    @GetMapping("/adminuser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String helloAdminUser() {
        return "hello admin and user.";
    }

    @GetMapping("/all")
    public String helloAll() {
        return "hello all";
    }

}
