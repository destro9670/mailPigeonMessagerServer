package ua.destro967.mailPigeon.errorHendling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.destro967.mailPigeon.security.jwt.JwtAuthenticationException;

//@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler({JwtAuthenticationException.class})
    public ResponseEntity handleAccessDeniedException(Exception ex, WebRequest request) {
        System.out.println("asdfasdfasdfasdfasdfasdfas");
        if(ex.getMessage().toLowerCase().indexOf("access is denied") > -1) {
            return new ResponseEntity<Object>("Unauthorized Access", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}