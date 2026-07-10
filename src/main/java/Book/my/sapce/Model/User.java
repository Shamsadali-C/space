package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue
    public Long id;

    @NotBlank(message="Must Enter The Name")
    private String name;
    @Email(message="Enter The Currect Format")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
