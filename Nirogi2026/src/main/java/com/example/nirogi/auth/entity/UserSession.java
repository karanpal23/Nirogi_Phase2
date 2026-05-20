package com.example.nirogi.auth.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String sessionId;

    private String jwtTokenHash;

    private LocalDateTime loginTime;

    private LocalDateTime expiryTime;

    private Boolean isActive;

    private String ipAddress;

    private String userAgent;

    // getters setters
}
