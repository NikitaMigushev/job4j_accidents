package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.AccidentUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(get("/users/logout").sessionAttr("user", new AccidentUser())) // Set a dummy user in the session
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login")); // Ensure the session attribute is removed
    }

    @Test
    void testRegistrationPage() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/register"));
    }
}