package com.example.nirogi.auth.serviceImpl;


import org.springframework.stereotype.Service;

import com.example.nirogi.auth.entity.UserSession;
import com.example.nirogi.auth.repository.SessionRepository;
import com.example.nirogi.auth.service.SessionService;
import com.example.nirogi.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository repository;

    public SessionServiceImpl(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createSession(
            User user,
            String token,
            HttpServletRequest request
    ) {

        UserSession session = new UserSession();

        session.setUserId(user.getId());
        session.setSessionId(UUID.randomUUID().toString());
        session.setJwtTokenHash(token);
        session.setLoginTime(LocalDateTime.now());
        session.setExpiryTime(LocalDateTime.now().plusHours(24));
        session.setIsActive(true);
        session.setIpAddress(request.getRemoteAddr());
        session.setUserAgent(request.getHeader("User-Agent"));

        repository.save(session);
    }

    @Override
    public boolean validateSession(String token) {

        return repository
                .findByJwtTokenHashAndIsActiveTrue(token)
                .filter(session ->
                        session.getExpiryTime()
                               .isAfter(LocalDateTime.now())
                )
                .isPresent();
    }

    @Override
    public void invalidateSession(String token) {

        repository
            .findByJwtTokenHashAndIsActiveTrue(token)
            .ifPresent(session -> {

                session.setIsActive(false);

                repository.save(session);
            });
    }
}
