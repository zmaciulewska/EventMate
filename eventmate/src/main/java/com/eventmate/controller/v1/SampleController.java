package com.eventmate.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@CrossOrigin(origins = "http://localhost:4200")
public class SampleController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
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
