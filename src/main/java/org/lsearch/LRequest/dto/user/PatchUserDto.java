package org.lsearch.LRequest.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchUserDto {
    @NotEmpty
    @Size(min = 5, message = "Name minimum length is 5")
    protected String name;

    @NotEmpty
    @Email
    protected String email;
}
