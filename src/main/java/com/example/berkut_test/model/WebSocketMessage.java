package com.example.berkut_test.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WebSocketMessage {
    private String username;
    private String text;
    private LocalDateTime sentAt;
}
