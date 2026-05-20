package com.example.nirogi.user.dto;




import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String username;

    private String role;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private Long designationId;

    private Long districtId;

    private Long facilityId;

    private Long facilityTypeId;

    private Boolean isActive;
}