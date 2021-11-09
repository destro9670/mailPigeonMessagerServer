package ua.destro967.mailPigeon.services;

import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.models.User;

import java.util.List;

public interface RoomService {

    List<Room> findByUser1OrUser2(User user);

}
