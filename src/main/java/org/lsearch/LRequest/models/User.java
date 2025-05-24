package org.lsearch.LRequest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.lsearch.LRequest.enums.UserRole;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public User() {}
    public User(String providerId, String email, String name) {
        this.providerId = providerId;
        this.email = email;
        this.name = name;
        this.role = UserRole.USER;
    }

    @Column(name = "provider_id", nullable = false, length = 100)
    @NotNull(message = "Provider id is not allowed to be null")
    private String providerId;

    @Column(name = "name", nullable = false, length = 100)
    @NotNull(message = "Name is not allowed to be null")
    @Size(min = 5, message = "Name minimum length is 5")
    private String name;

    @Column(name = "email", nullable = false)
    @NotNull(message = "Email is not allowed to be null")
    @Email
    private String email;

    @Column(name = "role", nullable = false, length = 20)
    @NotNull(message = "Role is not allowed to be null")
    private UserRole role;
}