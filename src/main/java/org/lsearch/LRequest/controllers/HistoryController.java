package org.lsearch.LRequest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.lsearch.LRequest.dto.user.RegisterUserDto;
import org.lsearch.LRequest.interfaces.CurrentUser;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("history")
@Validated
@RequiredArgsConstructor
public class HistoryController {

    @Value("${auth0.webhook.key}")
    private String webhookKey;
    private final UserService userService;

    @GetMapping("/list")
    public User getUserHistory(@CurrentUser User user) {
        return user;
    }
}
