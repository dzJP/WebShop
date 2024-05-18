package com.example.webshoplabb.dto;

import com.example.webshoplabb.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    private Role role;
    String profilePictureUrl;
}
