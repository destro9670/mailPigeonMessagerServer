package ua.destro967.mailPigeon.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.repositories.MessageRepository;
import ua.destro967.mailPigeon.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public Message findTopByRoomOrderByCreatedAsc(Room room) {
        return messageRepository.findTopByRoomOrderByCreatedAsc(room);
    }
}
