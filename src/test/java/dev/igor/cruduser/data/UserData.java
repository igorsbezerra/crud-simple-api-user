package dev.igor.cruduser.data;

import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.dto.response.PaginationResponse;
import dev.igor.cruduser.model.User;

import java.util.List;

public class UserData {
    public static ApiResponse<User> responseUsers() {
        return new ApiResponse<>(
                new PaginationResponse(0, 10, 1L, 1),
                List.of(User.create("igor"))
        );
    }

    public static List<User> listUsers() {
        return List.of(User.create("igor"));
    }
}
