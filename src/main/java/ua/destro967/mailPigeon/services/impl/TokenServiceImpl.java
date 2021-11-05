package ua.destro967.mailPigeon.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.repositories.TokenRepository;
import ua.destro967.mailPigeon.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl (TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }


    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    @Override
    public Token findByRefreshAndAccess(String refresh, String access) {
        return tokenRepository.findByRefreshAndAccess(refresh, access);
    }

    @Override
    public Token findByUser(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public Token findByAccess(String access) {
        return tokenRepository.findByAccess(access);
    }

    @Override
    public Token findByRefresh(String refresh) {
        return tokenRepository.findByRefresh(refresh);
    }
}
