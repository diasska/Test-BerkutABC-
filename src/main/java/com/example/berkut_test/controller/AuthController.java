package com.example.berkut_test.controller;

import com.example.berkut_test.model.User;
import com.example.berkut_test.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Аутентификация", description = "Регистрация и вход и выдача токена")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<?> register(@RequestBody User request) {
        authService.register(request);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }


    @PostMapping("/login")
    @Operation(summary = "Вход пользователя")
    public ResponseEntity<?> login(@RequestBody User request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }


    @PostMapping("/generate-token")
    @Operation(summary = "Генерация Telegram токена (авторизован)")
    public ResponseEntity<?> generateToken(Principal principal) {
        String token = authService.generateTelegramToken(principal.getName());
        return ResponseEntity.ok(Map.of("telegramToken", token));
    }

}
