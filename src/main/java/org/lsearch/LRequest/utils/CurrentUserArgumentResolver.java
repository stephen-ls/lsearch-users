package org.lsearch.LRequest.utils;

import lombok.RequiredArgsConstructor;
import org.lsearch.LRequest.exceptions.ResourceNotFoundException;
import org.lsearch.LRequest.exceptions.UnauthenticatedException;
import org.lsearch.LRequest.interfaces.CurrentUser;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.repositories.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.*;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Nullable
    @Override
    public User resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) throws Exception {

        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new UnauthenticatedException("Unauthenticated");
        }

        String providerId = principal.getSubject();
        var user = userRepository.getUserByProviderId(providerId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return user;
    }
}
