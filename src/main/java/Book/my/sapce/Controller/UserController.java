package Book.my.sapce.Controller;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;


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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }
}
