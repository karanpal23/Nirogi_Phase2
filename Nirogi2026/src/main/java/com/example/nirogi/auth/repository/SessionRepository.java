package com.example.nirogi.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nirogi.auth.entity.UserSession;

import java.util.Optional;

@Repository
public interface SessionRepository
        extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByJwtTokenHashAndIsActiveTrue(String token);
}
