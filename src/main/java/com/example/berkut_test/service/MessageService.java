package com.example.berkut_test.service;

import com.example.berkut_test.controller.sender.WebSocketMessageSender;
import com.example.berkut_test.model.Message;
import com.example.berkut_test.model.User;

import com.example.berkut_test.model.WebSocketMessage;
import com.example.berkut_test.repository.MessageRepository;
import com.example.berkut_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TelegramBotService botService;
    @Autowired
    private WebSocketMessageSender wsSender;

    public Message sendMessage(String username, String content) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Message message = new Message();
        message.setMessage(content);
        message.setCreatedAt(LocalDateTime.now());
        message.setUser(user);
        messageRepository.save(message);

        String formatted = String.format("%s, я получил от тебя сообщение:\n\n%s", user.getUsername(), content);
        botService.sendMessage(user.getTelegramChatId(), formatted);


        WebSocketMessage wsMessage = new WebSocketMessage();
        wsMessage.setUsername(user.getUsername());
        wsMessage.setText(content);
        wsMessage.setSentAt(message.getCreatedAt());

        wsSender.sendMessage(wsMessage);

        return message;
    }

    public List<Message> getAllMessagesForUser(String username) {
        return messageRepository.findAllByUserUsername(username);
    }
}
