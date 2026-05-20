package com.example.nirogi.auth.serviceImpl;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nirogi.auth.dto.AuthResponseDTO;
import com.example.nirogi.auth.dto.LoginRequestDTO;
import com.example.nirogi.auth.enums.CookieName;
import com.example.nirogi.auth.security.JwtService;
import com.example.nirogi.auth.service.AuthService;
import com.example.nirogi.auth.service.SessionService;
import com.example.nirogi.user.entity.User;
import com.example.nirogi.user.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            SessionService sessionService,
            JwtService jwtService,
            PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    public AuthResponseDTO login(
            LoginRequestDTO request,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        sessionService.createSession(user, token, httpRequest);

        Cookie cookie = new Cookie(
                CookieName.JWT_TOKEN.name(),
                token
        );

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);

        response.addCookie(cookie);

        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getDistrictId(),
                user.getFacilityId()
        );
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String token = jwtService.extractToken(request);

        if (token != null) {
            sessionService.invalidateSession(token);
        }

        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    @Override
    public AuthResponseDTO getCurrentUser() {

        User user = jwtService.getAuthenticatedUser();

        return new AuthResponseDTO(
                user.getUsername(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getDistrictId(),
                user.getFacilityId()
        );
    }
}
