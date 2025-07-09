package com.example.berkut_test.auth;

import com.example.berkut_test.config.JwtUtil;
import com.example.berkut_test.model.User;
import com.example.berkut_test.repository.UserRepository;
import com.example.berkut_test.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerNewUser_success() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");

        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded");

        authService.register(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("encoded");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded")).thenReturn(true);
        when(jwtUtil.generateToken("test")).thenReturn("jwt-token");

        User loginRequest = new User();
        loginRequest.setUsername("test");
        loginRequest.setPassword("1234");

        String token = authService.login(loginRequest);


        assertEquals("jwt-token", token);
    }
}
