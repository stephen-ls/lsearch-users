package org.lsearch.LRequest.services;

import lombok.extern.slf4j.Slf4j;
import org.lsearch.LRequest.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@Service
@ApplicationScope
public class UserService {

    public UserService() {
        log.info("User service is initialized");
    }

    public void printName(User user) {
        log.info(user.getName());
    }

    public void register(String providerId) {
        // register user here
    }
}
