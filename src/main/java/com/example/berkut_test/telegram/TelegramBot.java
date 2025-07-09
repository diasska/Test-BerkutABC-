package com.example.berkut_test.telegram;

import com.example.berkut_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("hello " + update);
        if (update.hasMessage()) {
            String token = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            userRepository.findByTgToken(token).ifPresentOrElse(user -> {
                user.setTelegramChatId(chatId);
                userRepository.save(user);
                send(chatId, "Connection is successful");
            }, () -> send(chatId, "Token is not correct"));
        }
    }

    public void send(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "berkut_bot";
    }

    @Override
    public String getBotToken() {
        return "8134650857:AAEywtUF3P2QFkhjbmw-odcfitl2bjpVOHA";
    }
}
