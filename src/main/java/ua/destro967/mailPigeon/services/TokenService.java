package ua.destro967.mailPigeon.services;

import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;

public interface TokenService  {

    Token save(Token token);
    void delete(Token token);
    Token findByRefreshAndAccess(String refresh, String access);


    Token findByUser(User user);
    Token findByAccess(String access);
    Token findByRefresh(String refresh);
}
