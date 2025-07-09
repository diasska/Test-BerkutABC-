package com.example.berkut_test.controller.sender;

import com.example.berkut_test.model.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageSender {
    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessage(WebSocketMessage message) {
        template.convertAndSend("/topic/message", message);
    }
}
