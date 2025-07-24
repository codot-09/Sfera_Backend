package com.example.sfera_backend.bot;

import com.example.sfera_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BotCode extends TelegramLongPollingBot {
    private final UserService userService;
    private final Map<Long, Boolean> waitingForNewPassword = new HashMap<>();
    private final List<Long> adminChatIds = List.of(7193645528L, 987654321L);

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    if (adminChatIds.contains(chatId)) {
                        sendMessage(chatId, "Salom!", false);
                    } else {
                        sendMessage(chatId, "Kirish taqiqlangan!", false);
                    }
                } else if (waitingForNewPassword.getOrDefault(chatId, false)) {
                    String response = userService.updatePassword(chatId, text);
                    sendMessage(chatId, response, false);
                    waitingForNewPassword.put(chatId, false);
                }
            }
        }
    }

    public void askNewPassword(Long chatId) {
        waitingForNewPassword.put(chatId, true);
        sendMessage(chatId, "Yangi parolni kiriting:", false);
    }

    private void sendMessage(Long chatId, String text, boolean askContact) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.setText(text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@sfera_register_bot";
    }

    @Override
    public String getBotToken() {
        return "7370660619:AAFt7WXQ-ydRpcH-VjzTwr5FsKsj1A8bX20";
    }
}
