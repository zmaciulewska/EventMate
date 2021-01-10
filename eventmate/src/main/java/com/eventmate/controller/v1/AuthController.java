package com.eventmate.controller.v1;

import com.eventmate.configuration.JwtProvider;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.security.JwtResponse;
import com.eventmate.dto.security.LoginForm;
import com.eventmate.dto.security.SignUpForm;
import com.eventmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getAuthorities(), userDetails.getUsername()));
    }

    @GetMapping("/user")
    public UserDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) authentication.getPrincipal();
        return principal;
    }



    @PostMapping("/signup")
    public ResponseEntity<CustomHttpResponse> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        CustomHttpResponse httpResponse = new CustomHttpResponse();
        if (userService.existsUserByUsername(signUpRequest.getUsername())) {
            httpResponse.setMessage("Login jest wykorzystywany przez innego użytkownika.");
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
        }
        if (userService.existsUserByEmail(signUpRequest.getEmail())) {
            httpResponse.setMessage("Email jest wykorzystywany przez innego użytkownika.");
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
        }
        userService.signUpUser(signUpRequest);
        httpResponse.setMessage("Użytkownik został zarejestrowany");
        httpResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(httpResponse);
    }


}
