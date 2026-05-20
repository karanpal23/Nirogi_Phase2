package com.example.nirogi.user.controller;


import com.example.nirogi.auth.util.SecurityUtil;
import com.example.nirogi.user.dto.CreateUserRequestDTO;
import com.example.nirogi.user.dto.UserResponseDTO;
import com.example.nirogi.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PostMapping
    public UserResponseDTO createUser(
            @RequestBody
            CreateUserRequestDTO request
    ) {

        // FIX BUG 2: Use SecurityUtil.getCurrentUserId() instead of hardcoded 1L
        // This ensures the actual logged-in user is recorded as the creator
        Long creatorId = SecurityUtil.getCurrentUserId();

        return service.createUser(
                request,
                creatorId
        );
    }


    @PatchMapping("/{userId}/disable")
    public void disableUser(
            @PathVariable Long userId
    ) {

        service.disableUser(userId);
    }
}
