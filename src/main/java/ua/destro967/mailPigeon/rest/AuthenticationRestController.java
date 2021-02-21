package ua.destro967.mailPigeon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.destro967.mailPigeon.dto.AuthenticationRequestDto;
import ua.destro967.mailPigeon.dto.RegistrationRequestDto;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.security.jwt.JwtTokenProvider;
import ua.destro967.mailPigeon.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + "not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();

            if (userService.findByUsername(username) != null) {
                throw new IllegalArgumentException("User with username: " + username + "already exist");
            }

            String password = requestDto.getPassword();

            if (!password.equals(requestDto.getConfirmPassword())) {
                throw new IllegalArgumentException("User  with username: " + username + "enter 'password' and 'confirmPassword' do not match");
            }

            String firstName = requestDto.getFirstName();

            if (firstName == null || firstName.equals("")) {
                throw new IllegalArgumentException("User  with username: " + username + "send empty field 'firstName'");
            }

            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
            user.setFirstName(firstName);

            String lastName = requestDto.getLastName();
            if (!(lastName == null || lastName.equals(""))) {
                user.setLastName(lastName);
            }

            userService.register(user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid input data");
        }
    }
}
