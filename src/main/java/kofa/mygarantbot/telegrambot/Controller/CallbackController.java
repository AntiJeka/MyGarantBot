package kofa.mygarantbot.telegrambot.Controller;

import kofa.mygarantbot.CallbackEvent;
import kofa.mygarantbot.GarantBot;
import kofa.mygarantbot.telegrambot.service.MessageSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CallbackController {
    MessageSenderService senderService;

    @PostMapping("/callback")
    public void handleCallback(@RequestBody CallbackEvent callbackEvent) {
        log.info("Получен CallbackEvent: {}", callbackEvent);

        // Обработка транзакции
        if ("to_service".equals(callbackEvent.getSide())) {
            log.info("Получен перевод от пользователя {}: {} койнов",
                    callbackEvent.getUserId(), callbackEvent.getSum());

            senderService.sendMessage(callbackEvent.getUserId(), "Получен перевод");
            // Дополнительная логика обработки перевода
        }
    }
}