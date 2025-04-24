package kofa.mygarantbot.handler;

import kofa.mygarantbot.constants.text.MenuTextEnum;
import kofa.mygarantbot.handler.keyboard.InlineKeyboard;
import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.model.Deal;
import kofa.mygarantbot.telegrambot.service.MessageSenderService;
import kofa.mygarantbot.telegrambot.service.shablon.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final UserService userService;
    Deal deal;
    MessageSenderService senderService;


    public BotApiMethod<?> commandHandler(Message message){
        String chatId = message.getChatId().toString();
        String text = message.getText();

        if (text.equals("/start")) {
            registerUser(message);
            return getStartMessage(chatId);
        }  else if (text.contains("/chat")){
            return getChatMessage(chatId, text);
        } else if(text.equals("/disconnection")){
            return getDisconnectionMessage(chatId);
        } else if(text.startsWith("/traid")){
            return getTraid(chatId, text);
        } else{
            return new SendMessage(chatId, "Ошибка: неизвестная команда");
        }
    }

    private String formatNumber(int number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRENCH);
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(number);
    }

    private String traid_text(String chatId, String text){
        String[] list = text.split(" ");

        int count_coin = Integer.parseInt(list[1].replace("к", "000"));
        double price = Double.parseDouble(list[2]);
        double commision = Double.parseDouble(String.valueOf(count_coin)) * 0.02;
        int dif = count_coin - (int) commision;
        int curs = (int) Math.ceil(Double.parseDouble(String.valueOf(count_coin)) / 1_000 * price);
        String str_price = String.valueOf(price);
        CRM userSeller = userService.findByUserId(Long.valueOf(chatId));
        CRM userBuyer = userService.findByUserId(userSeller.getChatId());
        return String.format(
                        "%s, подтверди открытие сделки:\n" +
                        "\uD83D\uDCE5 Покупатель: %s\n" +
                        "\uD83D\uDCB0 К оплате: %s\n" +
                        "♻Комиссия гаранта: %s\n" +
                        "\uD83D\uDCB3 К получению: %s\n" +
                        "➡Покупатель платит: %d₽ (курс %s за 1к)\n" +
                        "\n\uD83C\uDF10 Кнопка \"Продолжить\" открывает сделку.\n" +
                        "\nЕсли вы откажитесь от сделки после открытия, отменить её может только покупатель!\n" +
                        "\n\uD83D\uDD34 ВНИМАНИЕ, сделка ещё НЕ открыта!",
                userSeller.getName(), userBuyer.getName(), formatNumber(count_coin),
                formatNumber((int) commision), formatNumber(dif), curs, str_price
        );
    }


    private CRM registerUser(Message message){
        //Register user in database
        CRM user = CRM.builder()
                .name(message.getChat().getFirstName())
                .userId(message.getChatId())
                .status("IDLE").build();

        Deal deal = new Deal();
        deal.setUserId(message.getChatId());
        deal.setExchange(false);
        System.out.println("GG");
        return userService.saveUser(user);
    }


    private SendMessage getStartMessage(String chatId){
        InlineKeyboard inlineKeyboard = new InlineKeyboard();
        SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.WElCOME_TEXT.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboard.getKeyboardMenu());
        return sendMessage;
    }

    private SendMessage getChatMessage(String chatId, String text){
        if (deal.getStatus().equals("CHATTING") || deal.getStatus().equals("")) {
            return  new SendMessage(chatId, "⚠️ Вы уже в диалоге! Сначала завершите текущий.");
        } else {
            CRM user1 = userService.findByUserId(Long.valueOf(chatId));
            InlineKeyboard inlineKeyboard = new InlineKeyboard();
            //Установка chatId
            Long id = null;
            try {
                id = Long.valueOf(text.split(" ")[1]);
            } catch (Exception e){
                SendMessage sendMessage = new SendMessage(chatId, "Введите запрос корректно");
                sendMessage.setReplyMarkup(inlineKeyboard.getKeyboardMenu());
                return sendMessage;
            }
            CRM user2 = userService.findByUserId(id);
            user1.setChatId(user2.getUserId());
            user2.setChatId(user1.getUserId());

            senderService.sendMessage(user1.getChatId(), String.format("%s предлагает совершить сделку." +
                    "\nID: %d" +
                    "\nЕсли вы готовы совершить сделку нажмите Yes, если не готовы нажмите No", user1.getName(), Long.valueOf(chatId)),
                    inlineKeyboard.getKeyboardYesNo());
            return new SendMessage(chatId, "Ожидайте собеседника...");
        }
    }

    private SendMessage getDisconnectionMessage(String chatId){
        SendMessage sendMessage = new SendMessage(chatId, "Чат завершён");
        CRM user1 = userService.findByUserId(Long.valueOf(chatId));
        CRM user2 = userService.findByUserId(user1.getChatId());
        InlineKeyboard inlineKeyboard = new InlineKeyboard();

        //Логика закрытия сделки
        user1.setChatId(null);
        user2.setChatId(null);
        user1.setStatus("IDLE");
        user2.setStatus("IDLE");
        deal.getDiscussion().remove(Long.valueOf(chatId));

        senderService.sendMessage(Long.valueOf(chatId), "Чат завершён", inlineKeyboard.getKeyboardMenu());
        sendMessage.setReplyMarkup(inlineKeyboard.getKeyboardMenu());
        return sendMessage;
    }

    private SendMessage getTraid(String chatId, String text){
        CRM user1 = userService.findByUserId(Long.valueOf(chatId));
        CRM user2 = userService.findByUserId(user1.getChatId());
        InlineKeyboard inlineKeyboard = new InlineKeyboard();

        user1.setTraid_text(text);
        user2.setTraid_text(text);
        deal.setExchange(true);
        deal.setTraidText(text);

        String traid_text = traid_text(chatId, text);

        senderService.sendMessage(Long.parseLong(chatId), traid_text, inlineKeyboard.getKeyboardAcceptOrRefuse());
        return new SendMessage(chatId, traid_text);
    }
}
