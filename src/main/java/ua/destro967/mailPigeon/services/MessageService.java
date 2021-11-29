package ua.destro967.mailPigeon.services;

import org.springframework.context.support.MessageSourceAccessor;
import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;

import java.util.List;

public interface MessageService {
    Message findTopByRoomOrderByCreatedDesc(Room room);
    List<Message> findByRoom(Room room);

    Message save(Message message);

}
