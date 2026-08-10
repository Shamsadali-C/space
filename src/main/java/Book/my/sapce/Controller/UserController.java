package Book.my.sapce.Controller;

import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }



//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        return userService.register(user);
//
//    }

//    @PostMapping("/users")
//    public User CreateUser(@Valid @RequestBody User user) {
//        user.setRole(Role.USER);
//        return userService.save(user);
//    }
//
//    @PostMapping("/owner")
//    public User CreateOwner(@RequestBody User user){
//        user.setRole(Role.OWNER);
//        return userService.save(user);
//    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PutMapping("/{id}")
    public User updateuser(@PathVariable Long id,@RequestBody User user){
        user.setId(id);
        return userService.updateuser(id, user);

    }

//    @GetMapping("/{bookingId}")
//    public ResponseEntity<BookingDetailsDTO> getBookingDetails(
//            @PathVariable Long bookingId) {
//
//        return ResponseEntity.ok(bookingRepository.countByBookingStatus(bookingId));
//    }

//    @GetMapping
//    public List<User> getAllUsers() {
//
//        return userService.getAllUsers();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id)
//    {
//        return userService.getUserById(id);
//    }

    @GetMapping("/Profile")
    public ResponseEntity<?> getprofile(Authentication authentication){
        String username= authentication.getName();
        User user=userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/owner-request")
    public ResponseEntity<?> requestownerRole(Authentication authentication){
        String username= authentication.getName();

        User user=userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        return ResponseEntity.ok("Owner request sented successfully");
    }
}
