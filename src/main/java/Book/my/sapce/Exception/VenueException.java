package Book.my.sapce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VenueException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<String> error(MethodArgumentNotValidException ex){
        String error=ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new  ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
