package org.lsearch.LRequest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.lsearch.LRequest.dto.user.PatchUserDto;
import org.lsearch.LRequest.dto.user.RegisterUserDto;
import org.lsearch.LRequest.interfaces.AuthAspect;
import org.lsearch.LRequest.interfaces.CurrentUser;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.services.UserService;
import org.lsearch.LRequest.utils.UserContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("user")
@Validated
@RequiredArgsConstructor
public class UserController extends BaseController{

    @Value("${auth0.webhook.key}")
    private String webhookKey;
    private final UserService userService;

    @GetMapping("/me")
    @AuthAspect
    public User getProfile() {
        return UserContextHolder.getUser();
    }

    @PatchMapping("/me")
    @AuthAspect
    public void patchProfile(@RequestBody @Valid PatchUserDto userDto, Errors errors) throws BadRequestException {
        // TODO: Move to aspect
        this.processErrors(errors);

        if (userDto.getName() == null && userDto.getEmail() == null) {
            throw new BadRequestException("No patch parameters provided");
        }

        var user  = UserContextHolder.getUser();
        this.userService.updateUser(user.getId(), userDto);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterUserDto userDto, Errors errors) throws BadRequestException {
        // TODO: Move to aspect
        this.processErrors(errors);

        if (!Objects.equals(userDto.getAuth0Key(), this.webhookKey)) {
            throw new BadCredentialsException("Webhook key is missing");
        }

        this.userService.register(userDto);
    }
}
