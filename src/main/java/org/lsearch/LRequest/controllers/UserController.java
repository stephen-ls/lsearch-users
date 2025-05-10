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
@RequestMapping("user")
@Validated
@RequiredArgsConstructor
public class UserController {

    @Value("${auth0.webhook.key}")
    private String webhookKey;
    private final UserService userService;

    @GetMapping("/me")
    public User getProfile(@CurrentUser User user) {
        return user;
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterUserDto userDto, Errors errors) throws BadRequestException {
        if (errors.hasErrors()) {
            var output = errors.getAllErrors().stream().map(item -> item.getDefaultMessage()).toArray();
            throw new BadRequestException("There are some errors: " + Arrays.toString(output));
        }

        if (!Objects.equals(userDto.getAuth0Key(), this.webhookKey)) {
            throw new BadCredentialsException("Webhook key is missing");
        }

        this.userService.register(userDto);
    }
}
