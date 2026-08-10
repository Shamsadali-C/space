package Book.my.sapce.Controller;

import Book.my.sapce.DTO.LoginRequestDTO;
import Book.my.sapce.DTO.RegisterRequestDTO;
import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Service.AuthService;
import Book.my.sapce.Service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private JwtService jwtService;
    private final AuthService authService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService,
                          AuthService authService) {


        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        return ResponseEntity.ok(authService.login(Dto));

    }
}
