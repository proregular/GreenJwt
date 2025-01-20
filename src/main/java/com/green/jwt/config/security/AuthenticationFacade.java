package com.green.jwt.config.security;

import com.green.jwt.config.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class AuthenticationFacade {
    public static long getSignedUser(){
        return ((JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSignedUserId();
    }

}
