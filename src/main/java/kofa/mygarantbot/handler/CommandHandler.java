package kofa.mygarantbot.handler;

import kofa.mygarantbot.GarantBot;
import kofa.mygarantbot.SendOtherUser;
import kofa.mygarantbot.constants.MenuTextEnum;
import kofa.mygarantbot.database.DatabaseConnection;
import kofa.mygarantbot.keyboard.InlineKeyboard;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CommandHandler {

    private final GarantBot bot;

    public CommandHandler(GarantBot bot){
        this.bot = bot;
    }

    public void commandHandler(Long chatId, String text){
        InlineKeyboard keyboard = new InlineKeyboard();
        if (text.equals("/start")) {
            DatabaseConnection database = new DatabaseConnection(bot);
            database.addUser();
            bot.sendText(chatId, MenuTextEnum.WElCOME_TEXT.getMessage(), keyboard.getKeyboardMenu());
        }  else if (text.contains("/chat")){
            if (bot.userStates.containsKey(bot.getUserId())) {
                bot.sendText(bot.getUserId(), "⚠️ Вы уже в диалоге! Сначала завершите текущий.");
            } else {
                DatabaseConnection database = new DatabaseConnection(bot);
                String[] list = text.split(" ");
                for (int i = 0; i < list.length; i++) {
                    if (list[0].equals("/chat")) {
                        bot.setBuyerId(Long.valueOf(list[1]));
                        database.setOtherUserId(String.valueOf(bot.getBuyerId()));
                        bot.discussion.put(bot.getBuyerId(), bot.getUserId());
                        bot.setTraidMode(true);
                        break;
                    }
                }
                bot.setLastMes(bot.getUserName() + " предлагает совершить сделку." +
                        "\nID: " + bot.getUserId() + " " + "\nЕсли вы готовы совершить сделку напишите /Yes, если не готовы напишите /No");
                bot.sendText(bot.getBuyerId(), bot.getLastMes(), keyboard.getKeyboardYesNo());
                bot.sendText(bot.getUserId(), "Ожидайте собеседника...");
            }
        } else if(text.equals("/disconnection")){
            bot.sendText(bot.discussion.get(bot.getUserId()), "Чат завершен", keyboard.getKeyboardMenu());
            bot.sendText(bot.getUserId(), "Чат завершен", keyboard.getKeyboardMenu());
            bot.discussion.remove(bot.getUserId());
            bot.discussion.remove(bot.getBuyerId());
        } else if(text.startsWith("/traid")){
            SendOtherUser handler = new SendOtherUser(bot);

            bot.setTraidText(text);
            bot.setUnderwayTraid(true);

            String[] list = text.split(" ");

            int count_coin = Integer.valueOf(list[1].replace("к", "000"));


            double price = Double.valueOf(list[2]);

            String traid_text = traid_text(count_coin, price);
            handler.chatMessage(traid_text);
            bot.sendText(bot.getUserId(), traid_text, keyboard.getKeyboardContinue());
        }
    }

    private String formatNumber(int number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRENCH);
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(number);
    }

    private String traid_text(int count_coin, double price){
        DatabaseConnection database = new DatabaseConnection(bot);

        double commision = Double.valueOf(count_coin) * 0.02;
        int dif = count_coin - (int) commision;
        int curs = (int) Math.ceil(count_coin / 1_000 * price);
        String str_price = String.valueOf(price);

        return String.format(
                        "%s, подтверди открытие сделки:\n" +
                        "\uD83D\uDCE5 Покупатель: %s\n" +
                        "\uD83D\uDCB0 К оплате: %s\n" +
                        "♻\uFE0F Комиссия гаранта: %s\n" +
                        "\uD83D\uDCB3 К получению: %s\n" +
                        "➡\uFE0F Покупатель платит: %d₽ (курс %s за 1к)\n" +
                        "\n\uD83C\uDF10 Кнопка \"Продолжить\" открывает сделку.\n" +
                        "\n\u2757 Если вы откажитесь от сделки после открытия, отменить её может только покупатель!\n" +
                        "\n\uD83D\uDD34 ВНИМАНИЕ, сделка ещё НЕ открыта!",
                database.getUserName(bot.getUserId()), database.getUserName(bot.getBuyerId()), formatNumber(count_coin),
                formatNumber((int) commision), formatNumber(dif), curs, str_price
        );
    }

}
