package dev.igor.cruduser.service;

import dev.igor.cruduser.dto.request.UserCreateRequest;
import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.model.User;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    void create(UserCreateRequest request);
    ApiResponse<User> listUsers(PageRequest pageRequest);
}
