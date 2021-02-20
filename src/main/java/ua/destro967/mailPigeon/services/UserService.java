package ua.destro967.mailPigeon.services;

import ua.destro967.mailPigeon.models.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String userName);

    User findById(Long id);

    void delete(Long id);
}
