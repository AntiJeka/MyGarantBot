package kofa.mygarantbot.repository;

import kofa.mygarantbot.model.CRM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<CRM, Long> {
    CRM findUserByUserId(Long userId);
}
