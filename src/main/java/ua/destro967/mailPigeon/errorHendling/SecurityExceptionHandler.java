package ua.destro967.mailPigeon.errorHendling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ua.destro967.mailPigeon.security.jwt.JwtAuthenticationException;

@RestControllerAdvice
public class SecurityExceptionHandler {

    //@ExceptionHandler({JwtAuthenticationException.class})
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        System.out.println("asdfasdfasdfasdfasdfasdfas");
        if(ex.getMessage().toLowerCase().indexOf("access is denied") > -1) {
            return new ResponseEntity<Object>("Unauthorized Access", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}