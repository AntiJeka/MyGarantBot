package kofa.mygarantbot.repository;

import kofa.mygarantbot.model.CRM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<CRM, Long> {
    CRM findByUserId(Long userId);
}
