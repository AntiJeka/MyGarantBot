package kofa.mygarantbot.telegrambot.impl;

import kofa.mygarantbot.model.CRM;
import kofa.mygarantbot.repository.UsersRepository;
import kofa.mygarantbot.telegrambot.service.shablon.UserService;
import lombok.AllArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Service
@AllArgsConstructor
@Primary
@Component
public class UserServiceImpl implements UserService {
    private final UsersRepository repository;

    @Override
    public CRM saveUser(CRM user) {
        return repository.save(user);
    }

    @Override
    public CRM findByUserId(Long userId) {
        return repository.findUserByUserId(userId);
    }

    @Override
    public CRM updateUser(CRM user) {
        return repository.save(user);
    }
}
