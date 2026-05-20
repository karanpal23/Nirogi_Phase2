package com.example.nirogi.auth.security;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.nirogi.auth.service.SessionService;
import com.example.nirogi.user.entity.User;
import com.example.nirogi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private SessionService sessionService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) {
//
//        try {
//
//            String token = jwtService.extractToken(request);
//
//            if (token != null
//                    && jwtService.validateToken(token)
//                    && sessionService.validateSession(token)
//                    && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                String username = jwtService.extractUsername(token);
//
//                User user = userRepository
//                        .findByUsername(username)
//                        .orElse(null);
//
//                if (user != null) {
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    user,
//                                    null,
//                                    Collections.singletonList(
//                                            new SimpleGrantedAuthority("ROLE_" + user.getRole())
//                                    )
//                            );
//
//                    SecurityContextHolder
//                            .getContext()
//                            .setAuthentication(authentication);
//                }
//            }
//
//            filterChain.doFilter(request, response);
//
//        } catch (Exception ex) {
//
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
//    }
//}

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SessionService sessionService;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/auth/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {

        try {

            String token = jwtService.extractToken(request);

            if (token != null
                    && jwtService.validateToken(token)
                    && sessionService.validateSession(token)
                    && SecurityContextHolder
                       .getContext()
                       .getAuthentication() == null) {

                String username =
                        jwtService.extractUsername(token);

                Long userId = jwtService.extractUserId(token);  
                String role = jwtService.extractRole(token);  

                User user = userRepository
                        .findByUsername(username)
                        .orElse(null);

//                if (user != null && user.getIsActive()) {
                if (user != null && Boolean.TRUE.equals(user.getIsActive())) {

                	 AuthUserPrincipal principal =
                             new AuthUserPrincipal(userId, username, user.getRole());
//                    UsernamePasswordAuthenticationToken auth =
//                            new UsernamePasswordAuthenticationToken(
//                                    user,
//                                    null,
//                                    Collections.singletonList(
//                                            new SimpleGrantedAuthority(
//                                                    "ROLE_" + user.getRole()
//                                            )
//                                    )
//                            );
                	 
                	 UsernamePasswordAuthenticationToken auth =
                             new UsernamePasswordAuthenticationToken(
                                     principal,
                                     null,
                                     Collections.singletonList(
                                             new SimpleGrantedAuthority("ROLE_" + user.getRole())
                                     )
                             );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}