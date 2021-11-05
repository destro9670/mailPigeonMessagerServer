package ua.destro967.mailPigeon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token save(Token token);
    void delete(Token token);

    Token findByRefreshAndAccess(String refresh, String access);

    Token findByUser(User user);
    Token findByAccess(String access);
    Token findByRefresh(String refresh);
}
