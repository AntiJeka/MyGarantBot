package kofa.mygarantbot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class TelegramConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.userNameBot}")
    String botUserName;

    @Value("${database.url}")
    String databaseURL;

    @Value("${database.name}")
    String databaseName;

    @Value("${database.password}")
    String databasePassword;

    @Value("${api.url}")
    String apiURL;
}

