package dev.igor.cruduser.exception.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ErrorResponse(
        String err,
        String details,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<String> errors
) {
}
