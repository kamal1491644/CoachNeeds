package com.coach.needs.CoachNeeds.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthenticationUtils {
    private static final String ANONYMOUS_USER_NAME= "anonymousUser";

    public static boolean isAnonymous() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt) {
            return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims().containsKey("clientId");
        }

        return SecurityContextHolder.getContext().getAuthentication().getName().equals(ANONYMOUS_USER_NAME);
    }

    public static String getAuthenticatedUserSubjectId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getAuthenticationToken() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTokenValue();
    }
}
