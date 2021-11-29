package ua.destro967.mailPigeon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.destro967.mailPigeon.dto.MessageDto;
import ua.destro967.mailPigeon.dto.MessageInDto;
import ua.destro967.mailPigeon.dto.userList.MessageMinDto;
import ua.destro967.mailPigeon.dto.userList.RoomMinDto;
import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.services.MessageService;
import ua.destro967.mailPigeon.services.RoomService;
import ua.destro967.mailPigeon.services.TokenService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private TokenService tokenService;

    @GetMapping()
    public ResponseEntity getMessages(@RequestParam String roomUUID) {

        Room room = roomService.findByUuid(roomUUID);

        if (room == null)
            return ResponseEntity.status(404).build();


        List<Message> messageList = messageService.findByRoom(room);
        MessageDto messageDto;
        List<MessageDto> response = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss MMM dd");
        RoomMinDto roomMinDto;

        for (Message message: messageList) {
            messageDto = new MessageDto();

            messageDto.setStatus(message.isRead()? "read" : "no");
            messageDto.setMessage(message.getText());
            String dateTime = dateFormat.format(message.getCreated());
            messageDto.setTime(dateTime.split(" ")[0]);
            messageDto.setDate(dateTime.split(" ")[1]+ " " + dateTime.split(" ")[2]);
            roomMinDto = new RoomMinDto();
            roomMinDto.setUuid(roomUUID);
            roomMinDto.setName(message.getUser().getUsername());
            messageDto.setRoom(roomMinDto);
            response.add(messageDto);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody MessageInDto inputMessage, @RequestHeader("Authorization") String accessToken){
        accessToken =  accessToken.substring(7);
        Token token = tokenService.findByAccess(accessToken);

        User mainUser = token.getUser();

        Room room = roomService.findByUuid(inputMessage.getRoomUUID());
        if (room == null)
            return ResponseEntity.status(404).build();

        Message message = new Message();
        message.setUser(mainUser);
        message.setText(inputMessage.getMessage());
        message.setRoom(room);
        message.setCreated(new Date());

        message = messageService.save(message);

        MessageDto response = new MessageDto();

        RoomMinDto roomMinDto = new RoomMinDto();
        roomMinDto.setUuid(room.getUuid());
        roomMinDto.setName(message.getUser().getUsername());
        response.setRoom(roomMinDto);

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss MMM dd");
        String dateTime = dateFormat.format(message.getCreated());
        response.setTime(dateTime.split(" ")[0]);
        response.setDate(dateTime.split(" ")[1]+ " " + dateTime.split(" ")[2]);

        response.setStatus(message.isRead()? "read" : "no");
        response.setMessage(message.getText());

        return ResponseEntity.ok(response);
    }


}
