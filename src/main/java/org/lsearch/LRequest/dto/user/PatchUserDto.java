package org.lsearch.LRequest.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatchUserDto {
    @Size(min = 5, message = "Name minimum length is 5")
    protected String name;

    @Email
    protected String email;
}
