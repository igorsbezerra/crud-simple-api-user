package dev.igor.cruduser.controller;

import dev.igor.cruduser.data.UserData;
import dev.igor.cruduser.dto.request.UserCreateRequest;
import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.model.User;
import dev.igor.cruduser.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Nested
    class CreateUserTest {
        @Test
        void should_create_user() {
            UserCreateRequest request = new UserCreateRequest("igor");

            ResponseEntity<Void> response = userController.createUser(request);

            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Mockito.verify(userService, Mockito.times(1)).create(Mockito.any());
        }
    }

    @Nested
    class ListUsersTest {
        @Test
        void should_list_users() {
            Mockito.when(userService.listUsers(Mockito.any())).thenReturn(UserData.responseUsers());

            ResponseEntity<ApiResponse<User>> result = userController.listUsers(0, 10);

            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(1, result.getBody().items().size());
            Mockito.verify(userService, Mockito.times(1)).listUsers(Mockito.any());
        }
    }
}
