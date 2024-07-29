package dev.igor.cruduser.service.impl;

import dev.igor.cruduser.dto.request.UserCreateRequest;
import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.dto.response.PaginationResponse;
import dev.igor.cruduser.exception.UserAlreadyExists;
import dev.igor.cruduser.model.User;
import dev.igor.cruduser.repository.UserRepository;
import dev.igor.cruduser.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(UserCreateRequest request) {
        if (userExists(request.username())) {
            throw new UserAlreadyExists("User already exists!");
        }
        User user = User.create(request.username());
        repository.save(user);
    }

    @Override
    public ApiResponse<User> listUsers(PageRequest pageRequest) {
        Page<User> users = repository.findAll(pageRequest);
        return new ApiResponse<>(
                PaginationResponse.of(users),
                users.getContent()
        );
    }

    private boolean userExists(String username) {
        Optional<User> user = repository.findByUsername(username);
        return user.isPresent();
    }
}
