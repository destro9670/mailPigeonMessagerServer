package ua.destro967.mailPigeon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.models.User;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByUser1OrUser2(User user1, User user2);

    Room save(Room room);
}
