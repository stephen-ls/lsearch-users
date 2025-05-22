package org.lsearch.LRequest.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.lsearch.LRequest.enums.UserRole;

import java.util.Date;

@Data
public class User extends BaseEntity {

    @NotNull(message = "Provider id is not allowed to be null")
    private String providerId;

    @NotNull(message = "Name is not allowed to be null")
    @Size(min = 5, message = "Name minimum length is 5")
    private String name;

    @NotNull(message = "Email is not allowed to be null")
    @Email
    private String email;

    @NotNull(message = "Role is not allowed to be null")
    private UserRole role;
}

