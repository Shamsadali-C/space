package Book.my.sapce.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OwnerRequestDTO {
    @NotBlank
    private String venueName;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;
}
