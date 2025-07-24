package com.example.sfera_backend.bot;

import com.example.sfera_backend.dto.AdminDTO;
import com.example.sfera_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BotCode extends TelegramLongPollingBot {
    private final UserService userService;
    private final Set<Long> adminChatIds = new HashSet<>(List.of(7193645528L));
    private final Map<Long, String> adminState = new HashMap<>();
    private final Map<Long, String> verifyState = new HashMap<>();
    private final Map<Long, String> tempPhone = new HashMap<>();

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        if (!message.hasText() && !message.hasContact()) return;
        String text = message.hasText() ? message.getText().trim() : "";

        // START komandasi
        if ("/start".equalsIgnoreCase(text)) {
            handleStart(chatId);
            return;
        }

        // Verifikatsiya jarayoni
        if (verifyState.containsKey(chatId)) {
            handleVerification(chatId, message, text);
            return;
        }

        // Admin boshqaruvi (yangi admin qo'shish yoki o'chirish)
        if (adminState.containsKey(chatId)) {
            handleAdminState(chatId, text);
            return;
        }

        // Admin komandalarini tekshirish
        if (adminChatIds.contains(chatId)) {
            handleAdminCommands(chatId, text);
        }
    }

    private void handleStart(Long chatId) {
        if (!adminChatIds.contains(chatId)) {
            sendMessage(chatId, "❌ Kirish taqiqlangan! Siz admin emassiz.", false);
            return;
        }

        if (!userService.isVerified(chatId)) {
            sendContactRequest(chatId, "📱 Iltimos, telefon raqamingizni yuboring:");
            verifyState.put(chatId, "WAIT_PHONE");
        } else {
            showAdminMenu(chatId);
        }
    }

    private void handleVerification(Long chatId, Message message, String text) {
        String step = verifyState.get(chatId);

        if ("WAIT_PHONE".equals(step)) {
            if (message.hasContact()) {
                String phone = message.getContact().getPhoneNumber();
                tempPhone.put(chatId, phone);
                verifyState.put(chatId, "WAIT_NAME");
                sendMessage(chatId, "👤 To'liq ismingizni kiriting:", false);
            } else {
                sendContactRequest(chatId, "📱 Iltimos, telefon raqamingizni yuboring:");
            }
        } else if ("WAIT_NAME".equals(step)) {
            String phone = tempPhone.remove(chatId);
            String response = userService.verifyAdmin(chatId, phone, text);
            sendMessage(chatId, response, false);
            verifyState.remove(chatId);
            showAdminMenu(chatId);
        }
    }

    private void handleAdminState(Long chatId, String text) {
        String state = adminState.remove(chatId);

        if ("ADD_ADMIN".equals(state)) {
            try {
                Long newAdminId = Long.parseLong(text);
                String response = userService.saveAdmin(newAdminId);
                adminChatIds.add(newAdminId);
                sendMessage(chatId, "✅ " + response, false);
            } catch (NumberFormatException e) {
                sendMessage(chatId, "❌ Noto'g'ri chat ID! Iltimos, to'g'ri raqam kiriting.", false);
            }
        } else if ("REMOVE_ADMIN".equals(state)) {
            try {
                Long removeId = Long.parseLong(text);
                if (adminChatIds.remove(removeId)) {
                    sendMessage(chatId, "🗑️ Admin o'chirildi: " + removeId, false);
                } else {
                    sendMessage(chatId, "❌ Bunday admin topilmadi!", false);
                }
            } catch (NumberFormatException e) {
                sendMessage(chatId, "❌ Noto'g'ri chat ID! Iltimos, to'g'ri raqam kiriting.", false);
            }
        }
    }

    private void handleAdminCommands(Long chatId, String text) {
        if ("➕ Admin qo'shish".equalsIgnoreCase(text)) {
            adminState.put(chatId, "ADD_ADMIN");
            sendMessage(chatId, "➕ Yangi admin chat ID'sini kiriting:", false);
        } else if ("🗑️ Admin o'chirish".equalsIgnoreCase(text)) {
            adminState.put(chatId, "REMOVE_ADMIN");
            sendMessage(chatId, "🗑️ O'chirish uchun admin chat ID'sini kiriting:", false);
        } else if ("👥 Adminlar".equalsIgnoreCase(text)) {
            List<AdminDTO> admins = userService.getAllAdmins();
            if (admins.isEmpty()) {
                sendMessage(chatId, "😔 Hozircha adminlar yo'q.", false);
            } else {
                StringBuilder sb = new StringBuilder("👥 Adminlar ro'yxati:\n");
                for (AdminDTO admin : admins) {
                    sb.append(admin.chatId()).append(" -> ").append(admin.phone()).append(admin.fullName()).append("\n");
                }
                sendMessage(chatId, sb.toString(), false);
            }
        } else if ("🌐 Web sahifa".equalsIgnoreCase(text)) {
            sendMessage(chatId, "🌐 <a href=\"https://youtube.com\">Web sahifaga o'tish</a>", false);
        }
    }

    public void reminder(String message) {
        for (Long adminId : adminChatIds) sendMessage(adminId, "📢 " + message, false);
    }

    private void sendMessage(Long chatId, String text, boolean askContact) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.setText(text);
        msg.enableHtml(true);
        if (askContact) msg.setReplyMarkup(contactKeyboard());
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendContactRequest(Long chatId, String text) {
        sendMessage(chatId, text, true);
    }

    private ReplyKeyboardMarkup contactKeyboard() {
        KeyboardButton button = new KeyboardButton("📱 Telefon raqamni yuborish");
        button.setRequestContact(true);
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(List.of(row));
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    private void showAdminMenu(Long chatId) {
        SendMessage msg = new SendMessage(chatId.toString(), "📋 Admin menyusi:");

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("➕ Admin qo'shish"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("👥 Adminlar"));
        row2.add(new KeyboardButton("🗑️ Admin o'chirish"));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("🌐 Web sahifa"));

        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(List.of(row1, row2, row3));
        replyMarkup.setResizeKeyboard(true);

        msg.setReplyMarkup(replyMarkup);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
