package org.lsearch.LRequest.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.lsearch.LRequest.enums.UserRole;

@Data
public class User {

    @NotNull(message = "Id is not allowed to be null")
    private String id;

    @NotNull(message = "Provider id is not allowed to be null")
    private String providerId;

    @NotNull(message = "Name is not allowed to be null")
    @Size(min = 5, message = "Name minimum length is 5")
    private String name;

    @NotNull(message = "Role is not allowed to be null")
    private UserRole role;


}

