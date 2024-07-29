package dev.igor.cruduser.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty String username
) {
}
