package Book.my.sapce.Service;

import Book.my.sapce.DTO.LoginRequestDTO;
import Book.my.sapce.DTO.RegisterRequestDTO;
import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public  String register(RegisterRequestDTO dto) {

        if(userRepository.existsByUsername(dto.getUsername())){
            throw new RuntimeException("user Already exist");
        }
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        user.setRole(Role.USER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequestDTO dto) {

        User user = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Username"));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid password");
        }
        return jwtService.generateToken(user);
    }
}

