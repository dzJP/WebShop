package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.JwtAuthenticationResponse;
import com.example.webshoplabb.dto.SignInRequest;
import com.example.webshoplabb.dto.SignUpRequest;
import com.example.webshoplabb.models.Role;
import com.example.webshoplabb.models.User;
import com.example.webshoplabb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // SignUpRequest is used to create a new user account.
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        return JwtAuthenticationResponse.builder()
                .token(userService.generateJwtToken(userService.createAndSaveUser(request)))
                .build();
    }

    // SignInRequest is used to authenticate an existing user.
    public JwtAuthenticationResponse signin(SignInRequest request) {
        // Attempt to authenticate the user using the provided email and password.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Retrieve the user from the UserRepository based on the provided email.
        User user = userRepository.findByEmail(request.getEmail());

        if (user != null) {
            // Generate a JWT token for the authenticated user.
            String jwt = jwtService.generateToken(user);

            // Retrieve the user's role from the User entity.
            Role role = user.getRole();
            System.out.println(role.toString());

            // Return a JwtAuthenticationResponse containing the generated token.
            return JwtAuthenticationResponse.builder()
                    .token(jwt)
                    .role(role.toString())
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

}