package kofa.mygarantbot.telegrambot.Controller;

import kofa.mygarantbot.CallbackEvent;
import kofa.mygarantbot.telegrambot.service.SendUserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CallbackController {
    SendUserMessage sendUserMessage;

    @PostMapping("/callback")
    public void handleCallback(@RequestBody CallbackEvent callbackEvent) {
        log.info("Получен CallbackEvent: {}", callbackEvent);

        // Обработка транзакции
        if ("to_service".equals(callbackEvent.getSide())) {
            log.info("Получен перевод от пользователя {}: {} койнов",
                    callbackEvent.getUserId(), callbackEvent.getSum());

            sendUserMessage.sendMessage(callbackEvent.getUserId(), "Получен перевод");
            // Дополнительная логика обработки перевода
        }
    }
}