package Book.my.sapce.Service;

import Book.my.sapce.Exception.ResourceNotFoundException;
import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserService  {

    @Autowired
    public UserRepository userRepository;




    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateuser(Long id ,User u){
        User user=userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setEmail(u.getEmail());
        return userRepository.save(user);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<User> getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("user not found with:"+id));

        return ResponseEntity.ok(user);

    }

//    public User register(LoginDTO loginDTO) {
//        System.out.println("Request Body" + loginDTO);
//        User u= new User();
//             u.setUsername(loginDTO.getUsername());
//             u.setPassword(loginDTO.getPassword());
//
//           return userRepository.save(u);
//    }


//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public User register (User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return user;
//    }


//    public User register(User user){
//        return userRepository.save(user);
//    }


}
