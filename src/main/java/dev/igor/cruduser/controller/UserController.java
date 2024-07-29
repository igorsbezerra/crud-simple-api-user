package dev.igor.cruduser.controller;

import dev.igor.cruduser.dto.request.UserCreateRequest;
import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.model.User;
import dev.igor.cruduser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserCreateRequest request) {
        service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<User>> listUsers(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        final var users = service.listUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(users);
    }
}
