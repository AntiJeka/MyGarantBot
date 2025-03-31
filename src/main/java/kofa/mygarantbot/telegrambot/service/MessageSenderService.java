package kofa.mygarantbot.telegrambot.service;

import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.telegrambot.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSenderService {
    private final DefaultAbsSender telegramBot;
    private final UserServiceImpl service;

    public void sendMessage(Long chatId, String text){
        sendMessage(chatId, text, null);
    }

    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboard){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        if (inlineKeyboard != null){
            sendMessage.setReplyMarkup(inlineKeyboard);
        }

        try{
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e){
            log.info("Error: Couldn't send a message");
        }
    }

    public SendMessage sendMessage(Message message){
        CRM user = service.findByUserId(message.getChatId());
        return new SendMessage(String.valueOf(user.getChatId()), message.getText());
    }
}
