package Book.my.sapce.Repository;

import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     long countByRole(Role role);
            Optional<User >findByUsername(String username);

}
