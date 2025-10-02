package com.facundo.auth_service.controller;

import com.facundo.auth_service.dto.RegisterRequest;
import com.facundo.auth_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(1L);

        ResponseEntity<?> response = authController.register(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void register_emailDuplicated() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        when(userService.registerUser(any(RegisterRequest.class))).thenThrow(new IllegalArgumentException("Email already registered"));

        ResponseEntity<?> response = authController.register(request);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already registered", response.getBody());
    }
}
