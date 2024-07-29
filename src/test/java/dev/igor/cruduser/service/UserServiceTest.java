package dev.igor.cruduser.service;

import dev.igor.cruduser.data.UserData;
import dev.igor.cruduser.dto.request.UserCreateRequest;
import dev.igor.cruduser.dto.response.ApiResponse;
import dev.igor.cruduser.exception.UserAlreadyExists;
import dev.igor.cruduser.model.User;
import dev.igor.cruduser.repository.UserRepository;
import dev.igor.cruduser.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;



    @Nested
    class CreateUserTest {
        @Test
        void should_create_user() {
            UserCreateRequest request = new UserCreateRequest("igor");
            Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

            userService.create(request);

            Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        }

        @Test
        void should_not_create_user_when_username_already_exists() {
            UserCreateRequest request = new UserCreateRequest("igor");
            Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                    .thenReturn(Optional.of(new User("igor")));

            Assertions.assertThrows(UserAlreadyExists.class, () -> userService.create(request));
            Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.anyString());
            Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
        }
    }

    @Nested
    class ListUsersTest {
        @Test
        void should_list_users() {
            PageImpl<User> page = new PageImpl<>(UserData.listUsers());
            PageRequest request = PageRequest.of(0, 10);
            Mockito.when(userRepository.findAll(request)).thenReturn(page);

            ApiResponse<User> result = userService.listUsers(request);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.items().size());
            Mockito.verify(userRepository, Mockito.times(1)).findAll(request);
        }
    }
}
