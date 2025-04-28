package kofa.mygarantbot;


import kofa.mygarantbot.handler.CommandHandler;
import kofa.mygarantbot.handler.CallbackQueryHandler;
import kofa.mygarantbot.handler.TradeHandler;
import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.telegrambot.impl.UserServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public final class GarantBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;

    TradeHandler tradeHandler;
    CallbackQueryHandler callbackQueryHandler;
    CommandHandler commandHandler;
    UserServiceImpl userServiceImpl;

    public GarantBot(SetWebhook setWebhook, TradeHandler tradeHandler, CallbackQueryHandler callbackQueryHandler,
                     CommandHandler commandHandler, UserServiceImpl userServiceImpl){
        super(setWebhook);
        this.commandHandler = commandHandler;
        this.tradeHandler = tradeHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.userServiceImpl = userServiceImpl;
    }


    public final Map<Long, BotState> userStates = new HashMap<>();
    public final HashMap<Long, Long> discussion = new HashMap<>();

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try{
            return onUpdateReceived(update);
        } catch (IllegalArgumentException e){
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "Я к такому не был готов");
        } catch (Exception e){
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "WTF...\nОбратитесь к программисту.");
        }
    }


    private enum BotState {
        IDLE,
        WAITING_PARTNER,
        CHATTING
    }

    public BotApiMethod<?> onUpdateReceived(Update update) throws IOException{
        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.callbackHandler(callbackQuery);
        } else {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.getText().equals("/start")){
                    return commandHandler.commandHandler(message);
                }
                CRM user = userServiceImpl.findByUserid(message.getChatId());
                String status = user.getStatus();

                if (message.getText().startsWith("/")) {
                    return commandHandler.commandHandler(message);
                } else if (status.equals("CHATTING")) {
                    SendMessage sendMessage = new SendMessage(String.valueOf(user.getChatId()), message.getText());
                    return sendMessage;
                }
            }
        }
        return null;
    }

}