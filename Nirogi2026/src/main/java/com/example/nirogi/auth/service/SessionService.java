package com.example.nirogi.auth.service;


import javax.servlet.http.HttpServletRequest;

import com.example.nirogi.user.entity.User;

public interface SessionService {

    void createSession(
            User user,
            String token,
            HttpServletRequest request
    );

    boolean validateSession(String token);

    void invalidateSession(String token);
}
