package Book.my.sapce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException( RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
                  response.put("status", 404);
                  response.put("error", "Not Found");
                  response.put("message", ex.getMessage());
        return ResponseEntity .status(HttpStatus.NOT_FOUND) .body(response); }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {

//            Map<String, String> error = new HashMap<>();
//            error.put("message", error.get());
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", 404,
                            "error", "Not Found",
                            "message", ex.getMessage()
                    ));
        }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameExists(UsernameAlreadyExistsException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Conflict");
        response.put("message", ex.getMessage());
        response.put("status", 409);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidLogin(
            InvalidLoginException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("message", ex.getMessage());
        response.put("status", 401);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}








