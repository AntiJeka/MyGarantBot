package kofa.mygarantbot.handler;

import kofa.mygarantbot.GarantBot;
import kofa.mygarantbot.constants.text.MenuTextEnum;
import kofa.mygarantbot.constants.text.TraidBuyMenuEnum;
import kofa.mygarantbot.handler.keyboard.InlineKeyboard;
import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.model.Deal;
import kofa.mygarantbot.telegrambot.service.CoinTransferService;
import kofa.mygarantbot.telegrambot.impl.UserServiceImpl;
import kofa.mygarantbot.telegrambot.service.MessageSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class CallbackQueryHandler {

    UserServiceImpl service;
    GarantBot bot;
    MessageSenderService senderService;
    Deal deal;

    public BotApiMethod<?> callbackHandler(CallbackQuery callbackQuery){
        InlineKeyboard keyboard = new InlineKeyboard();
        String chatId = callbackQuery.getId();
        String userText = callbackQuery.getData();

        CRM user = service.findByUserId(Long.valueOf(chatId));

        if(userText.equalsIgnoreCase("Вопросы")) {
            SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.MENU_QUESTIONS.getMessage());
            sendMessage.setReplyMarkup(keyboard.getKeyboardQuestionsMenu());
            return sendMessage;
        } else if(userText.equals("Сделка")){
            SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.TRAID_TEXT.getMessage());
            sendMessage.setReplyMarkup(keyboard.getKeyboardTraid());
            return sendMessage;
        } else if(userText.equals("Вернуться в главное меню")){
            SendMessage sendMessage = new SendMessage(chatId, "Главное меню");
            sendMessage.setReplyMarkup(keyboard.getKeyboardMenu());
        } else if(userText.equals("Назад")){
            SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.MENU_QUESTIONS.getMessage());
            sendMessage.setReplyMarkup(keyboard.getKeyboardQuestionsMenu());
            return sendMessage;
        } else if(userText.equals("Вопрос 1")){
            SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.QUESTIONS_ONE.getMessage());
            sendMessage.setReplyMarkup(keyboard.getKeyboardQuestion());
            return sendMessage;
        } else if(userText.equals("Вопрос 2")){
            SendMessage sendMessage = new SendMessage(chatId, MenuTextEnum.QUESTIONS_TWO.getMessage());
            sendMessage.setReplyMarkup(keyboard.getKeyboardQuestion());
            return sendMessage;
        } else if(userText.equals("Yes")){
            return getYesMessage(chatId);
        } else if(userText.equals("No")){
            return getNoMessage(chatId);
        } else if(userText.equals("Перевёл койны")){
            //System.out.println("good");
        } else if(userText.equals("Accept")){
            return getAcceptMessage(chatId);
        } else if(userText.equals("Refuse")){
            return getRefuseMessage(chatId);
        } else if(userText.equals("Получил деньги")){
            return getSendMoneyMessage(chatId);
        }
        return new SendMessage(chatId, "Error: How?");
    }



    private String formatNumber(int number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRENCH);
        symbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(number);
    }

    private String giveMeCoin(CRM user){
        int count_coin = Integer.parseInt(user.getTraid_text().split(" ")[1]);
        return String.format(
                        "Прежде чем сделка открылась перейди по ссылке: https://t.me/byteappbot/app?startapp=transfer-b759e3bb8c16" +
                        " и переведи на счёт гаранта: " + formatNumber(count_coin) + " койнов\n" +
                        "Когда переведёте койны на счёт бота нажмите \"Перевёл койны\"" +
                        "\n" +
                        "\uD83D\uDD34Внимание! Переведите точную сумму, что была указана в трейде!" +
                        " В противном случае бот не засчитает перевод!");
    }

//    private String traid_text(CRM user){
//        int count_coin;
//        double price;
//        int curs = (int) Math.ceil(count_coin / 1_000 * price);
//        String str_price = String.valueOf(price);
//        CRM userBuyer = service.findByUserId(user.getChatId());
//
//        return String.format(
//                        "\uD83D\uDFE1 Сделка открыта\n" +
//                        "\n" +
//                        "\uD83D\uDD10 Гарант получил койны: %s\n" +
//                        "\n" +
//                        "\uD83D\uDCE5 %s: отправь %d руб продавцу ИЛИ нажми кнопку \"Отказаться\", " +
//                        "если ты передумал покупать или не устраивает курс\n" +
//                        "\n" +
//                        "\uD83D\uDCE4 %s: как только ты получишь деньги, нажми кнопку \"Получил деньги\", " +
//                        "и койны сразу будут отправлены покупателю.\n" +
//                        "\n" +
//                        "\uD83D\uDD17 Сумма сделки: %d ₽ (курс %s₽ за 1к). " +
//                        "Это конечная сумма и стороны сделки неправе менять курс походу сделки.",
//                formatNumber(count_coin), userBuyer.getName(), curs, user.getName(),
//                curs, str_price);
//    }

    private SendMessage getYesMessage(String chatId){
        CRM user = service.findByUserId(Long.valueOf(chatId));
        deal.getDiscussion().put(user.getUserId(), user.getChatId());

        senderService.sendMessage(user.getChatId(), TraidBuyMenuEnum.TRAID_DEAL.getMessage());
        return new SendMessage(chatId, TraidBuyMenuEnum.TRAID_DEAL.getMessage());
    }

    private SendMessage getNoMessage(String chatId){
        CRM user1 = service.findByUserId(Long.valueOf(chatId));
        CRM user2 = service.findByUserId(user1.getChatId());
        InlineKeyboard inlineKeyboard = new InlineKeyboard();

        user1.setChatId(null);
        user2.setChatId(null);
        user1.setStatus("IDLE");
        user2.setStatus("IDLE");

        deal.getDiscussion().remove(Long.valueOf(chatId));
        deal.setStatus("IDLE");

        SendMessage sendMessage = new SendMessage(String.valueOf(user2.getUserId()), TraidBuyMenuEnum.TRAID_NO.getMessage());
        senderService.sendMessage(Long.valueOf(chatId), "Вы отказались присоединятсья в чат");
        sendMessage.setReplyMarkup(inlineKeyboard.getKeyboardMenu());
        return sendMessage;
    }

    private BotApiMethod<?> getAcceptMessage(String chatId) {
        CRM userSeller = service.findByUserId(Long.valueOf(chatId));
        String text = giveMeCoin(userSeller);
        deal.setExchange(true);

        return new SendMessage(String.valueOf(userSeller.getChatId()), text);
    }

    private BotApiMethod<?> getRefuseMessage(String chatId) {
        CRM userBuyer = service.findByUserId(Long.valueOf(chatId));
        CRM userSeller = service.findByUserId(userBuyer.getChatId());

        userSeller.setExchange(false);
        userBuyer.setExchange(false);
        userSeller.setTraid_text(null);
        userBuyer.setTraid_text(null);

        senderService.sendMessage(userSeller.getUserId(), TraidBuyMenuEnum.TRAID_REFUSE_BUYER.getMessage());
        return new SendMessage(String.valueOf(userBuyer.getUserId()), TraidBuyMenuEnum.TRAID_REFUSE_BUYER.getMessage());
    }

    private BotApiMethod<?> getSendMoneyMessage(String chatId){
        CRM userSeller = service.findByUserId(Long.valueOf(chatId));
        String traidText = userSeller.getTraid_text();
        String[] list = traidText.split(" ");

        int countCoin = Integer.parseInt(list[1].replace("к", "000"));

        // Вычисляем комиссию и итоговую сумму
        double commission = countCoin * 0.02;
        int finalAmount = countCoin - (int) commission;

        long recipientUserId = userSeller.getChatId();

        // Создаем сервис для перевода койнов
        CoinTransferService transferService = new CoinTransferService();
        boolean transferSuccess = transferService.transferCoins(recipientUserId, finalAmount);

        // Формируем сообщение для пользователя
        CRM userBuyer = service.findByUserId(recipientUserId);
        String message;
        if (transferSuccess) {
            message = "\uD83D\uDFE2 Сделка закрыта\n\n" +
                    "Бот перевёл " + formatNumber(finalAmount) +
                    " койнов пользователю с именем " + userBuyer.getName();
        } else {
            message = "⚠️ Ошибка при переводе койнов. Пожалуйста, попробуйте позже.";
        }

        // Отправляем сообщение
        senderService.sendMessage(userBuyer.getUserId(), message);
        return new SendMessage(String.valueOf(userSeller.getUserId()), message);
    }
}
