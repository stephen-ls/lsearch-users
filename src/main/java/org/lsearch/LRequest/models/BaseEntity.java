package org.lsearch.LRequest.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @NotNull(message = "Id is not allowed to be null")
    private int id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
