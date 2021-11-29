package ua.destro967.mailPigeon.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.repositories.MessageRepository;
import ua.destro967.mailPigeon.services.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public Message findTopByRoomOrderByCreatedDesc(Room room) {
        return messageRepository.findTopByRoomIdOrderByCreatedDesc(room.getId());
    }

    @Override
    public List<Message> findByRoom(Room room) {
        return messageRepository.findByRoomIdOrderByCreatedAsc(room.getId());
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
