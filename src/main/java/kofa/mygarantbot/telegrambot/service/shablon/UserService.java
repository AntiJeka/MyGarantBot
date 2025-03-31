package kofa.mygarantbot.telegrambot.service.shablon;

import kofa.mygarantbot.model.CRM;

public interface UserService {
    CRM saveUser(CRM user);
    CRM findByUserId(Long userId);
    CRM updateUser(CRM user);
}
