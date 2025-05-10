package org.lsearch.LRequest.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.lsearch.LRequest.enums.UserRole;
import org.lsearch.LRequest.interfaces.CurrentUser;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("")
@Validated
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public User index(
            @RequestParam()
            @NotNull(message = "Name is not allowed to be null")
            @Size(min = 5, message = "Name should be greater than 4")
            String name,

            @RequestParam()
            @NotNull(message = "Role is not allowed to be null")
            UserRole role
    ) {
        var user = new User();
        user.setName(name);
        user.setRole(role);
        return user;
    }

    @GetMapping("/{name}")
    public User admin(@PathVariable() String name) {
        User user = new User();
        user.setName(name);
        user.setRole(UserRole.ADMIN);

        return user;
    }

    @PostMapping("/")
    public User submit(@RequestBody @Valid User user, Errors errors) throws BadRequestException {
        if (errors.hasErrors()) {
            var output = errors.getAllErrors().stream().map(item -> item.getDefaultMessage()).toArray();
            throw new BadRequestException("There are some errors: " + Arrays.toString(output));
        } else {
            return user;
        }
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        throw new RuntimeException("Test");
        // return ResponseEntity.ok("Public Endpoint Working fine !");
    }
}
