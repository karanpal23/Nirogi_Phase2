package com.example.nirogi.auth.security;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserPrincipal {

 private Long userId;
 private String username;
 private String role;
}
