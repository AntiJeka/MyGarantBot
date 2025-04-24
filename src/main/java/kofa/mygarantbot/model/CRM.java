package kofa.mygarantbot.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class CRM {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private Long userId;
    private Long chatId;
    private String status;
    private boolean isExchange;
    private String traid_text;
}
