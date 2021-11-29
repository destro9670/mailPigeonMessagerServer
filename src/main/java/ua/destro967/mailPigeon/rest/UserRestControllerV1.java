package ua.destro967.mailPigeon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.destro967.mailPigeon.dto.AddUser;
import ua.destro967.mailPigeon.dto.UserDto;
import ua.destro967.mailPigeon.dto.userList.MessageMinDto;
import ua.destro967.mailPigeon.dto.userList.RoomMinDto;
import ua.destro967.mailPigeon.dto.userList.UserListDto;
import ua.destro967.mailPigeon.models.Message;
import ua.destro967.mailPigeon.models.Room;
import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.services.MessageService;
import ua.destro967.mailPigeon.services.RoomService;
import ua.destro967.mailPigeon.services.TokenService;
import ua.destro967.mailPigeon.services.UserService;
import ua.destro967.mailPigeon.utils.UserListDtoComparator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private MessageService messageService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getUserList(@RequestHeader("Authorization") String accessToken) {

        accessToken =  accessToken.substring(7);
        Token token = tokenService.findByAccess(accessToken);

        User user = token.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Room> rooms = roomService.findByUser1OrUser2(user);
        List<UserListDto> userListDto = new ArrayList<>();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");




        for (Room room: rooms) {
            UserListDto tmp = new UserListDto();
            RoomMinDto roomDto = new RoomMinDto();
            MessageMinDto messageDto = new MessageMinDto();
            User tmpUser;

            if (room.getUser1() == user){
                tmpUser = room.getUser2();
            }else{
                tmpUser = room.getUser1();
            }
            roomDto.setName(tmpUser.getUsername());
            roomDto.setUuid(room.getUuid());

            Message message = messageService.findTopByRoomOrderByCreatedDesc(room);
            if (message == null){
                messageDto.setMessage("");
                messageDto.setTime("");
                messageDto.setStatus("");
            }else {
                messageDto.setMessage(message.getText());
                messageDto.setTime(dateFormat.format(message.getCreated()));
                messageDto.setStatus(message.isRead() ? "read" : "no");
            }
            tmp.setMessage(messageDto);
            tmp.setRoom(roomDto);
            userListDto.add(tmp);
        }

        Collections.sort(userListDto, new UserListDtoComparator());
        Collections.reverse(userListDto);
        return new ResponseEntity(userListDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addRoom(@RequestBody AddUser addUser, @RequestHeader("Authorization") String accessToken){
        accessToken =  accessToken.substring(7);
        Token token = tokenService.findByAccess(accessToken);

        User mainUser = token.getUser();
        if (mainUser == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User newUser = userService.findByUsername(addUser.getUsername());
        if (newUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Room room = new Room();
        room.setUser1(mainUser);
        room.setUser2(newUser);
        room = roomService.save(room);

        UserListDto tmp = new UserListDto();
        RoomMinDto roomDto = new RoomMinDto();
        MessageMinDto messageDto = new MessageMinDto();

        roomDto.setName(newUser.getUsername());
        roomDto.setUuid(room.getUuid());

        messageDto.setMessage("");
        messageDto.setTime("");
        messageDto.setStatus("");

        tmp.setMessage(messageDto);
        tmp.setRoom(roomDto);

        return ResponseEntity.ok(tmp);
    }

}
