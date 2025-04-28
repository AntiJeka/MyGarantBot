package kofa.mygarantbot.telegrambot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.IOException;

@Service
public class SendUserMessage {
    @Value("${telegram.bot-token}")
    String BOT_TOKEN;

    public void sendMessage(Long CHAT_ID, String text, InlineKeyboardMarkup keyboardMarkup) {
        try {
            // Формируем URL для API Telegram
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
            URL url = new URL(urlString);

            // Открываем соединение
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Формируем JSON-тело запроса
            String jsonInputString;
            if (keyboardMarkup != null) {
                String keyboardJson = """
                {
                    "inline_keyboard": [
                        [
                            {"text": "Yes", "callback_data": "Yes"},
                            {"text": "No", "callback_data": "No"}
                        ]
                    ]
                }
                """;
                jsonInputString = String.format("""
                {
                    "chat_id": "%s",
                    "text": "%s",
                    "reply_markup": %s
                }
                """, CHAT_ID, escapeJson(text), keyboardJson);
            } else {
                jsonInputString = String.format("{\"chat_id\": \"%s\", \"text\": \"%s\"}",
                        CHAT_ID, escapeJson(text));
            }

            // Отправляем запрос
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    private static String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
