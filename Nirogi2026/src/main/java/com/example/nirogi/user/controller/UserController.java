package com.example.nirogi.user.controller;


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

        Long creatorId = 1L;

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
