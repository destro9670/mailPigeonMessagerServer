package ua.destro967.mailPigeon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.destro967.mailPigeon.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
