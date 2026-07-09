package Book.my.sapce.Service;

import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public ResponseEntity<User> getUserById(Long id){
    Optional<User> user = userRepository.findById(id);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
