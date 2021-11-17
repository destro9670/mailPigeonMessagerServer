package ua.destro967.mailPigeon.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.repositories.RoomRepository;
import ua.destro967.mailPigeon.services.RoomService;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findByUser1OrUser2(User user){
        return roomRepository.findByUser1OrUser2(user,user);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
