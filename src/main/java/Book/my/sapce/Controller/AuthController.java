package Book.my.sapce.Controller;

import Book.my.sapce.DTO.LoginRequestDTO;
import Book.my.sapce.DTO.RegisterRequestDTO;
import Book.my.sapce.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService=authService;
    }

//    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(10);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO dto){
//        User user= new User();
//        user.setUsername(dto.getUsername());
//        user.setEmail(dto.getEmail());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
//        user.setRole(Role.USER);

       return ResponseEntity.ok(authService.register(dto));//" user registered successfully"
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO  Dto){

       try {
           return ResponseEntity.ok(authService.login(Dto));
       } catch (RuntimeException e) {
          return ResponseEntity
                  .status(HttpStatus.UNAUTHORIZED)
                  .body(e.getMessage());
       }
    }
}
