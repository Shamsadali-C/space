package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message="Must Enter The Name")
    @Column(nullable = false,unique = true)
    private String username;

    @Size(min =8,message = "password must be 8 character")
    private String password;

    @Email(message="Enter The Correct Format")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


}
