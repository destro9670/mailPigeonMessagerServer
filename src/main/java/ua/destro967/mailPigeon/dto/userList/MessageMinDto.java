package ua.destro967.mailPigeon.dto.userList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageMinDto {
    private String message;
    private String status;
    private String time;

}
