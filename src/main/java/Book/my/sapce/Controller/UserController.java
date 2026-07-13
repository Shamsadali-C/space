package Book.my.sapce.Controller;

import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import Book.my.sapce.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/users")
    public User CreateUser(@Valid @RequestBody User user) {
        user.setRole(Role.USER);
        return userService.save(user);
    }

    @PostMapping("/owner")
    public User CreateOwner(@RequestBody User user){
        user.setRole(Role.OWNER);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
