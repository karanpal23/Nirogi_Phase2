package com.example.nirogi.user.dto;

import lombok.Data;

@Data
public class CreateUserRequestDTO {

    private String role;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private Long designationId;

    private Long districtId;

    private Long facilityId;

    private Long facilityTypeId;
}
