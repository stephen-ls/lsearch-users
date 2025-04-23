package org.lsearch.LRequest.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotNull(message = "Provider id is not allowed to be null")
    private String providerId;

    @NotNull(message = "Provider id is not allowed to be null")
    private String key;
}
