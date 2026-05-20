package com.example.nirogi.auth.util;



import com.example.nirogi.auth.security.AuthUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

 public static Long getCurrentUserId() {

     Authentication auth =
             SecurityContextHolder.getContext().getAuthentication();

     if (auth == null || !(auth.getPrincipal() instanceof AuthUserPrincipal)) {
         throw new RuntimeException("Unauthorized");
     }

     return ((AuthUserPrincipal) auth.getPrincipal()).getUserId();
 }

 public static String getCurrentUserRole() {

     Authentication auth =
             SecurityContextHolder.getContext().getAuthentication();

     if (auth == null || !(auth.getPrincipal() instanceof AuthUserPrincipal)) {
         throw new RuntimeException("Unauthorized");
     }

     return ((AuthUserPrincipal) auth.getPrincipal()).getRole();
 }
}
