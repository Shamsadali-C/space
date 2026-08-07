package Book.my.sapce.DTO;

import Book.my.sapce.Model.Role;
import Book.my.sapce.Model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VenueRequestDTO {

    private String venueName;
    private String location ;
    private Double price;
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
//    private Role role=Role.OWNER;
}
