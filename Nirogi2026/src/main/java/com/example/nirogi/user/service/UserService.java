package com.example.nirogi.user.service;

import java.util.List;

import com.example.nirogi.user.dto.CreateUserRequestDTO;
import com.example.nirogi.user.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(
            CreateUserRequestDTO request,
            Long createdBy
    );

    void disableUser(Long userId);

    List<UserResponseDTO> getUsersByDistrict(
            Long districtId
    );
}
