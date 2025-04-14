package org.lsearch.LRequest.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.lsearch.LRequest.enums.UserRole;
import org.lsearch.LRequest.models.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.annotation.RequestScope;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

@Component
@Aspect
// @Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequestScope
@Slf4j
public class AuthAspect {
    private User user;

    @Before("@annotation(org.lsearch.LRequest.interfaces.AuthAspect)")
    public void authenticate(ProceedingJoinPoint joinPoint) throws Throwable {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var subject = principal.getSubject();
        System.out.println(subject);

        this.user = new User();
        user.setName("Artem");
        user.setRole(UserRole.ADMIN);

        joinPoint.proceed();
    }
}