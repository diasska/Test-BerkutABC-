package com.example.berkut_test.controller;

import com.example.berkut_test.model.Message;
import com.example.berkut_test.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> request, Principal principal) {
        String content = request.get("message");
        Message message = messageService.sendMessage(principal.getName(), content);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(Principal principal) {
        List<Message> messages = messageService.getAllMessagesForUser(principal.getName());
        return ResponseEntity.ok(messages);
    }
}
