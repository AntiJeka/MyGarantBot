package kofa.mygarantbot.config;

import kofa.mygarantbot.GarantBot;
import kofa.mygarantbot.handler.CallbackQueryHandler;
import kofa.mygarantbot.handler.CommandHandler;
import kofa.mygarantbot.handler.TradeHandler;
import kofa.mygarantbot.telegrambot.impl.UserServiceImpl;
import kofa.mygarantbot.telegrambot.service.shablon.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(telegramConfig.getWebhookPath()).build();
    }

    @Bean
    public GarantBot springWebhookBot(SetWebhook setWebhook, TradeHandler tradeHandler, CallbackQueryHandler callbackQueryHandler,
                                      CommandHandler commandHandler, UserService userService) {
        GarantBot bot = new GarantBot(setWebhook, tradeHandler, callbackQueryHandler, commandHandler, (UserServiceImpl) userService);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
