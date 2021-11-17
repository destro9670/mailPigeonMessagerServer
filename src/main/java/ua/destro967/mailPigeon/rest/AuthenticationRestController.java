package ua.destro967.mailPigeon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ua.destro967.mailPigeon.dto.AuthenticationRequestDto;
import ua.destro967.mailPigeon.dto.RegistrationRequestDto;
import ua.destro967.mailPigeon.dto.TokenDto;
import ua.destro967.mailPigeon.models.Token;
import ua.destro967.mailPigeon.models.User;
import ua.destro967.mailPigeon.security.jwt.JwtTokenProvider;
import ua.destro967.mailPigeon.services.TokenService;
import ua.destro967.mailPigeon.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "/api/v1/")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.tokenService = tokenService;
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

            Token tokens = tokenService.findByUser(user);
            tokens.setAccess(token);
            tokens.setRefresh(generateRefreshToken());
            tokenService.save(tokens);

            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("accessToken", tokens.getAccess());
            response.put("refreshToken", tokens.getRefresh());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto requestDto) {
        try {
            System.out.println(requestDto.getPassword() + "\n" +
            requestDto.getUsername());
            String username = requestDto.getUsername();

            if (userService.findByUsername(username) != null) {
                throw new IllegalArgumentException("User with username: " + username + "already exist");
            }

            String password = requestDto.getPassword();

            User user = new User();
            user.setPassword(password);
            user.setUsername(username);

            Token token = new Token();
            token.setUser(userService.register(user));
            token.setAccess("");
            token.setRefresh("");
            tokenService.save(token);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid input data");
        }
    }

    @PostMapping("/check-auth")
    public ResponseEntity checkAuth(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");

        String accessToken = bearerToken.substring(7);

        Token token = tokenService.findByAccess(accessToken);

        if(token == null){
            return ResponseEntity.status(401).build();
        }
        Map<String, String> response = new HashMap<>();

        User user = token.getUser();

        response.put("username", user.getUsername());
        response.put("accessToken", token.getAccess());
        response.put("refreshToken", token.getRefresh());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody TokenDto tokenDto){

        Token token = tokenService.findByRefreshAndAccess(tokenDto.getRefreshToken(), tokenDto.getAccessToken());

        if (token==null){
            return ResponseEntity.status(401).build();
        }

        User user = token.getUser();

        token.setAccess(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
        token.setRefresh(generateRefreshToken());
        tokenService.save(token);

        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("accessToken", token.getAccess());
        response.put("refreshToken", token.getRefresh());

        return ResponseEntity.ok(response);

    }

    private String generateRefreshToken(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }


    @PostMapping("/logout")
    public ResponseEntity logOut(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");

        String accessToken = bearerToken.substring(7);

        Token token = tokenService.findByAccess(accessToken);
        tokenService.delete(token);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/test")
    public ResponseEntity test (){
        Map<Object, Object> response = new HashMap<>();
        response.put("username", 123);
        response.put("token", 1231231);

        return ResponseEntity.ok(response);
    }
}
