package kofa.mygarantbot.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class Deal {
    private Long userId;;
    private boolean deal_mode;
    private String status;
    private HashMap<Long, Long> discussion;
    private boolean isExchange;
    private String traidText;
}