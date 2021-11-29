package ua.destro967.mailPigeon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.destro967.mailPigeon.dto.userList.RoomMinDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String message;
    private String date;
    private String time;
    private String status;
    private RoomMinDto room;
}
