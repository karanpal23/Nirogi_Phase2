package com.example.nirogi.auth.dto;

import com.example.nirogi.user.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String username;

    private String role;

    private String firstName;

    private String lastName;

    private Long districtId;

    private Long facilityId;
}