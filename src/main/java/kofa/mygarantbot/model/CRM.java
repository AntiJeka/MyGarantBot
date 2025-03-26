package kofa.mygarantbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Traid {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long userId;
    private Long chatId;
    private String status;
}
