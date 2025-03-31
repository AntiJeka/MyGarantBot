package kofa.mygarantbot.telegrambot.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinTransferService {

    private static final Logger logger = LoggerFactory.getLogger(CoinTransferService.class);
    private static final String TRANSFER_API_URL = "https://byteapi.su/service/transfer";
    private static final String ACCESS_TOKEN = "3161436749fda52f5f5cd181921cbe42c074dcb6cdfcc1f0cb6deb2fd6adfa4d"; // Ваш токен доступа

    public boolean transferCoins(long userId, int sum) {
        RestTemplate restTemplate = new RestTemplate();

        // Заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");

        // Тело запроса
        String requestBody = String.format(
                "{\"access_token\": \"%s\", \"user_id\": %d, \"sum\": %d}",
                ACCESS_TOKEN, userId, sum
        );

        // Создаем HTTP-запрос
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Выполняем POST-запрос
            ResponseEntity<String> response = restTemplate.postForEntity(TRANSFER_API_URL, request, String.class);

            // Проверяем статус ответа
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Перевод успешно выполнен: {}", response.getBody());
                return true;
            } else {
                logger.error("Ошибка при переводе: {}", response.getBody());
                return false;
            }
        } catch (Exception e) {
            logger.error("Ошибка при выполнении запроса: {}", e.getMessage());
            return false;
        }
    }
}