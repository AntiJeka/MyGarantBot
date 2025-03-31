package kofa.mygarantbot;

import lombok.Data;

@Data
public class CallbackEvent {
    private String uniqueId;      // Уникальный ID перевода
    private long userId;          // ID пользователя
    private String serviceId;     // ID сервиса
    private String side;          // Направление перевода (to_service или to_user)
    private int sum;              // Сумма перевода
    private String createdAt;     // Время перевода (в формате строки)
    private String comment;       // Комментарий к переводу
}