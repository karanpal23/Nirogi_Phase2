package com.example.nirogi.auth.service;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.nirogi.auth.dto.AuthResponseDTO;
import com.example.nirogi.auth.dto.LoginRequestDTO;

public interface AuthService {

    AuthResponseDTO login(
            LoginRequestDTO request,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    );

    void logout(
            HttpServletRequest request,
            HttpServletResponse response
    );

    AuthResponseDTO getCurrentUser();
}
