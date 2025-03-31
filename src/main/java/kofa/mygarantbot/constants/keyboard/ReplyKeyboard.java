package kofa.mygarantbot.constants.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboard {

    public static ReplyKeyboardMarkup getKeyboardMenu(){
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup replyKeyboardMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Сделка");
        row.add("Вопросы");

        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getKeyboardQuestions(){
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup replyKeyboardMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Вопрос 1");
        row.add("Вопрос 2");
        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Назад");
        keyboardRows.add(row);

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getKeyboardTraid(){
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup replyKeyboardMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Назад");
        replyKeyboardMarkup.setResizeKeyboard(true);

        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}
