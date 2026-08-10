package Book.my.sapce.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterRequestDTO {

    @NotBlank(message="Must Enter The Name")
    @Column(nullable = false,unique = true)
    private  String username;
//    private  String secondname;

    @Email(message="Enter The Correct Format")
    private  String email;

    @Size(min =8,message = "password must be 8 character")
    private  String password;
//    private  String confirmpassword;

}
