package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message="Must Enter The Name")
    private String name;
    @Email(message="Enter The Correct Format")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


}
