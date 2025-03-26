package kofa.mygarantbot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class SendOtherUser {
    private final GarantBot bot;
    public SendOtherUser(GarantBot bot){
        this.bot = bot;
    }

    public void chatMessage(String text){
        Long buyerId = bot.discussion.get(bot.getUserId());
        if (buyerId != null) {
            bot.sendText(buyerId, text);
        } else {
            bot.sendText(bot.getUserId(), "Попробуйте переподключиться");
        }
    }
    public void chatMessage(String text, InlineKeyboardMarkup keyboard){
        Long buyerId = bot.discussion.get(bot.getUserId());
        if (buyerId != null) {
            bot.sendText(buyerId, text, keyboard);
        } else {
            bot.sendText(bot.getUserId(), "Попробуйте переподключиться");
        }
    }
}
