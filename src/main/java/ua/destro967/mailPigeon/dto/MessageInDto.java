package ua.destro967.mailPigeon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInDto {
    private String message;
    private String roomUUID;
}
