package ua.destro967.mailPigeon.errorHendling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.destro967.mailPigeon.security.jwt.JwtAuthenticationException;

import javax.security.sasl.AuthenticationException;

//@RestController
public class ErrorHEndler {

  //  @ResponseStatus(value= HttpStatus.UNAUTHORIZED,
      //      reason="Data integrity violation")  // 409
    //@ExceptionHandler(AuthenticationException.class)
    public void conflict() {
        System.out.println("asdfasdfasdf");
    }
}
