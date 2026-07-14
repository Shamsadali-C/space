package Book.my.sapce.DTO;

import Book.my.sapce.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueRequest {

    private String venueName;
    private String location ;
    private Double price;
    private Integer capacity;
    private User user;

}
