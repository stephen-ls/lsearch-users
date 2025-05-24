package org.lsearch.LRequest.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.lsearch.LRequest.exceptions.ResourceNotFoundException;
import org.lsearch.LRequest.exceptions.UnauthenticatedException;
import org.lsearch.LRequest.services.UserService;
import org.lsearch.LRequest.utils.UserContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Aspect
@RequestScope
@RequiredArgsConstructor
public class AuthAspect {
    private final UserService userService;

    @Before("@annotation(org.lsearch.LRequest.interfaces.AuthAspect)")
    public void loadUser(JoinPoint joinPoint) throws Throwable {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new UnauthenticatedException("Unauthenticated");
        }

        String providerId = principal.getSubject();
        var user = userService.getUserByProviderId(providerId);;
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        UserContextHolder.setUser(user);
    }

    @After("@annotation(org.lsearch.LRequest.interfaces.AuthAspect)")
    public void clearUser() {
        UserContextHolder.clear();
    }
}