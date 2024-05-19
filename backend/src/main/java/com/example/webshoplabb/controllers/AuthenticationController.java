package com.example.webshoplabb.controllers;

import com.example.webshoplabb.dto.JwtAuthenticationResponse;
import com.example.webshoplabb.dto.SignInRequest;
import com.example.webshoplabb.dto.SignUpRequest;
import com.example.webshoplabb.services.AuthenticationService;
import com.example.webshoplabb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}
