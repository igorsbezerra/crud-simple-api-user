package dev.igor.cruduser.dto.response;

import java.util.List;

public record ApiResponse<T>(
        PaginationResponse pagination,
        List<T> items
) {
}
