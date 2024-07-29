package dev.igor.cruduser.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class UserTest {
    @Test
    void testCreateUser() {
        String username = "testUser";
        User user = User.create(username);

        Assertions.assertNotNull(user);
        Assertions.assertNull(user.getId()); // ID deve ser nulo porque ainda não foi persistido
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertNotNull(user.getCreatedAt());

        // Verificar se createdAt é uma data no formato esperado
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime createdAt = LocalDateTime.parse(user.getCreatedAt(), formatter);
        Assertions.assertTrue(createdAt.isBefore(now) || createdAt.isEqual(now));
    }

    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("anotherUser");
        user.setCreatedAt("2024-07-29T10:15:30");

        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("anotherUser", user.getUsername());
        Assertions.assertEquals("2024-07-29T10:15:30", user.getCreatedAt());
    }
}
