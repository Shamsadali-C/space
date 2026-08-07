package Book.my.sapce.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterRequestDTO {

    private  String username;
//    private  String secondname;
    private  String email;
    private  String password;
//    private  String confirmpassword;

}
