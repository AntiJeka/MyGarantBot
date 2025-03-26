package kofa.mygarantbot.constants;

import kofa.mygarantbot.GarantBot;

public enum TraidBuyMenuEnum {

    TRAID_YES("Пользователь согласился провести с вами сделку"),
    TRAID_NO("Пользователь отказался присоединяться к вам в чат"),
    TRAID_DEAL("Добро пожаловать в ваш личный чат для безопасных сделок!" +
            "\nВнимание: НЕ ПЕРЕВОДИ деньги ДО ОТКРЫТИЯ сделки!" +
            "\nЧтобы открыть сделку, напиши /traid \"количество койнов\" + \"курс к 1к\"" +
            "\nПример: /traid 10к 1.2" +
            "\nУказывайте количество койнов в целом количестве!" +
            "\nЧтобы завершить чат напишите /disconnection"),
    TRAID_REFUSE_USER("Вы отказалась от сделки"),
    TRAID_REFUSE_BUYER(" отказался от вашей сделки.");

    private final String message;


    TraidBuyMenuEnum(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
