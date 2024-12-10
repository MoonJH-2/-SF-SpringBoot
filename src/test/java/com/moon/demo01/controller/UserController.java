package com.moon.demo01.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moon.demo01.dto.UserRequest;
import com.moon.demo01.dto.UserResponse;
import com.moon.demo01.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper(); // ObjectMapper 직접 초기화
    }

    @Test
    void testRegisterUser() throws Exception {
        UserRequest request = new UserRequest("test@example.com", "password123", "Test User");
        UserResponse response = new UserResponse("1", "test@example.com", "Test User");

        when(userService.registerUser(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserResponse> responses = List.of(
                new UserResponse("1", "test1@example.com", "User1"),
                new UserResponse("2", "test2@example.com", "User2")
        );

        when(userService.getAllUsers()).thenReturn(responses);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetUserById() throws Exception {
        UserResponse response = new UserResponse("1", "test@example.com", "Test User");

        when(userService.getUserById(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }
}
