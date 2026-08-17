package Book.my.sapce.DTO;

import Book.my.sapce.Model.OwnerRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequestResponseDTO {

    private Long id;

    private Long userId;

    private String username;

    private String venueName;

    private String address;

    private String phone;

    private OwnerRequestStatus status;
}
