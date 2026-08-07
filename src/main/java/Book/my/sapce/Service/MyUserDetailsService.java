package Book.my.sapce.Service;

import Book.my.sapce.Model.User;
import Book.my.sapce.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));


//                if(user==null) {
//                    System.out.println("user not found");
//                   throw new UsernameNotFoundException("User not found");
//                }

//                return  new UserPrinciple(user);


        return org.springframework.security.core.userdetails.User.builder()
                .username((user.getUsername()))
                .password((user.getPassword()))
                .authorities(user.getRole().name())
                .build();



    }
}

