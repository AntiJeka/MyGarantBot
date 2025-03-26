package kofa.mygarantbot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineKeyboard {

    public InlineKeyboardMarkup getKeyboardMenu(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineLine = new ArrayList<>();
        List<InlineKeyboardButton> inlineLine1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Сделка");
        button1.setCallbackData("Сделка");
        inlineLine1.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Вопросы");
        button2.setCallbackData("Вопросы");
        inlineLine1.add(button2);

        inlineLine.add(inlineLine1);

        markupInline.setKeyboard(inlineLine);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardQuestionsMenu(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Вопрос 1");
        button1.setCallbackData("Вопрос 1");
        rowInline1.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Вопрос 2");
        button2.setCallbackData("Вопрос 2");
        rowInline1.add(button2);
        /*
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Вопрос 3");
        button3.setCallbackData("Вопрос 3");
        rowInline2.add(button3);
        */
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Вернуться в главное меню");
        button4.setCallbackData("Вернуться в главное меню");
        rowInline3.add(button4);

        rowsInline.add(rowInline1);
        //rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardTraid(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Вернуться в главное меню");
        button1.setCallbackData("Вернуться в главное меню");
        rowInline1.add(button1);

        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardQuestion(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Назад");
        button1.setCallbackData("Назад");
        rowInline1.add(button1);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Вернуться в главное меню");
        button2.setCallbackData("Вернуться в главное меню");
        rowInline2.add(button2);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardYesNo(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Yes");
        button1.setCallbackData("Yes");
        rowInline.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("No");
        button2.setCallbackData("No");
        rowInline.add(button2);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardContinue(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Продолжить");
        button1.setCallbackData("Продолжить");
        rowInline1.add(button1);

        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardRefuse(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Отказаться");
        button1.setCallbackData("Отказаться");
        rowInline1.add(button1);

        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardMoney(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Получил деньги");
        button1.setCallbackData("Получил деньги");
        rowInline1.add(button1);

        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public InlineKeyboardMarkup getKeyboardTransferCoin(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Перевёл койны");
        button1.setCallbackData("Перевёл койны");
        rowInline1.add(button1);

        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
