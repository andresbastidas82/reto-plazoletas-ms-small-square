package com.pragma.ms_small_square.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalProvider {
    private UserPrincipal getPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getUserId() {
        return getPrincipal().getId();
    }


    public String getUserName() {
        return getPrincipal().getName();
    }

    public String getUserEmail() {
        return getPrincipal().getEmail();
    }
}
