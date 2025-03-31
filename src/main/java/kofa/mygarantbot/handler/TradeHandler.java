package kofa.mygarantbot.handler;

import kofa.mygarantbot.GarantBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TradeHandler {
    private Map<Long, TradeInfo> trades = new HashMap<>(); // Хранение информации о сделках по userId


    public void startTrade(long userId, int coins, GarantBot bot) {
        trades.put(userId, new TradeInfo(coins, Instant.now()));
        log.info("Сделка начата для пользователя {}. Койны: {}", userId, coins);
    }

    public boolean isValidTransaction(long userId, int coins, Instant transferTime) {
        TradeInfo tradeInfo = trades.get(userId);

        // Проверяем, что сделка начата
        if (tradeInfo == null) {
            log.warn("Сделка не начата для пользователя {}", userId);
            return false;
        }

        // Проверяем, что количество койнов совпадает
        if (tradeInfo.getCoins() != coins) {
            log.warn("Неверное количество койнов для пользователя {}. Ожидалось: {}, получено: {}",
                    userId, tradeInfo.getCoins(), coins);
            return false;
        }

        // Проверяем, что перевод выполнен в течение 30 минут после начала сделки
        Instant tradeEndTime = tradeInfo.getStartTime().plusSeconds(30 * 60); // 30 минут
        if (transferTime.isAfter(tradeEndTime)) {
            log.warn("Время для перевода истекло для пользователя {}", userId);
            return false;
        }

        log.info("Перевод корректен для пользователя {}", userId);
        return true;
    }

    public void endTrade(long userId) {
        trades.remove(userId);
        log.info("Сделка завершена для пользователя {}", userId);
    }

    public TradeInfo getTradeInfo(long userId) {
        return trades.get(userId);
    }

    /**
     * Внутренний класс для хранения информации о сделке.
     */
    private static class TradeInfo {
        private final int coins;       // Количество койнов
        private final Instant startTime; // Время начала сделки

        public TradeInfo(int coins, Instant startTime) {
            this.coins = coins;
            this.startTime = startTime;
        }

        public int getCoins() {
            return coins;
        }

        public Instant getStartTime() {
            return startTime;
        }
    }
}
