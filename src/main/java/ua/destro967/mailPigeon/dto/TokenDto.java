package ua.destro967.mailPigeon.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String refreshToken;
    private String accessToken;
}
