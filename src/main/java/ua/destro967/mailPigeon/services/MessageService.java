package ua.destro967.mailPigeon.services;

import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;

public interface MessageService {
    Message findTopByRoomOrderByCreatedAsc(Room room);

}
