package com.example.nirogi.auth.controller;


import org.springframework.web.bind.annotation.*;

import com.example.nirogi.auth.dto.AuthResponseDTO;
import com.example.nirogi.auth.dto.LoginRequestDTO;
import com.example.nirogi.auth.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Tag(name = "Authentication", description = "User authentication APIs")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Login user and create session")
    @PostMapping("/login")
    public AuthResponseDTO login(
            @RequestBody LoginRequestDTO request,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        return service.login(request, httpRequest, response);
    }

    @Operation(summary = "Logout user and invalidate session")
    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        service.logout(request, response);
    }

    @Operation(summary = "Get current logged-in user info")
    @GetMapping("/me")
    public AuthResponseDTO me() {

        return service.getCurrentUser();
    }
}