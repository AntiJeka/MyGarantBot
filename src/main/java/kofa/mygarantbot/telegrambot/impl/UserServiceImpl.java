package kofa.mygarantbot.telegrambot.impl;

import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.repository.UsersRepository;
import kofa.mygarantbot.telegrambot.service.shablon.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    private final UsersRepository repository;

    @Override
    public CRM saveUser(CRM user) {
        return repository.save(user);
    }

    @Override
    public CRM findByUserid(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public CRM updateUser(CRM user) {
        return repository.save(user);
    }
}
