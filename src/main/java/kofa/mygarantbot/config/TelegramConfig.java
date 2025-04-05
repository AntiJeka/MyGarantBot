package kofa.mygarantbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
public class TelegramConfig {

    @Value("${telegram.bot-name}")
    String botName;

    @Value("${telegram.bot-token}")
    String botToken;

    @Value("${telegram.webhook-path}")
    String webhookPath;
}

