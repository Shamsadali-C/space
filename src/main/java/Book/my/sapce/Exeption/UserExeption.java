package Book.my.sapce.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExeption {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex){
        String errorMessage=ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return new  ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

}
