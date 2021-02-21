package ua.destro967.mailPigeon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.destro967.mailPigeon.models.Role;
import ua.destro967.mailPigeon.models.Status;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.repositories.RoleRepository;
import ua.destro967.mailPigeon.repositories.UserRepository;
import ua.destro967.mailPigeon.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN UserServiceImpl.register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN UserServiceImpl.getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String userName) {
        User result = userRepository.findByUsername(userName);
        log.info("IN UserServiceImpl.findByUserName - user: {} found by username: {}", result, userName);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN UserServiceImpl.findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN UserServiceImpl.findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN UserServiceImpl.delete - user with id: {} successfully deleted", id);
    }
}
