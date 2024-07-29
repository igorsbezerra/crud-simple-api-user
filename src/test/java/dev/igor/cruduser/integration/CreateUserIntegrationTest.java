package dev.igor.cruduser.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.igor.cruduser.model.User;
import dev.igor.cruduser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CreateUserIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private ObjectMapper mapper;

    @Test
    void should_create_user() throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("username", "igor");
        String json = mapper.writeValueAsString(objectNode);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_not_create_user() throws Exception {
        userRepository.save(User.create("igor"));
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("username", "igor");
        String json = mapper.writeValueAsString(objectNode);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void should_not_create_user_when_send_username_empty() throws Exception {
        userRepository.save(User.create("igor"));
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("username", "");
        String json = mapper.writeValueAsString(objectNode);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
