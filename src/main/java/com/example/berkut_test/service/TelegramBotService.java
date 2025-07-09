package com.example.berkut_test.service;

import com.example.berkut_test.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {

    @Autowired
    private TelegramBot bot;
    public void sendMessage(Long chatId, String text) {
        if(chatId != null) {
            bot.send(chatId, text);
        }
    }
}
