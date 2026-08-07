package Book.my.sapce.Controller;

import Book.my.sapce.DTO.LoginRequestDTO;
import Book.my.sapce.DTO.RegisterRequestDTO;
import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(10);




    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO dto){
        User user= new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        return ResponseEntity.ok(" user registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO  Dto){
        User user=userRepository.findByUsername(Dto.getUsername())
                .orElse(null);
        if(user==null){
            return ResponseEntity
                    .badRequest().body("User Not Found");
        }
        if(!passwordEncoder.matches(Dto.getPassword(),
                user.getPassword())){
            return ResponseEntity.badRequest().body("Invalid password");
        }
        return ResponseEntity.ok("Login Successfull");

    }
}
