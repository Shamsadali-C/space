package Book.my.sapce.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VenueResponce {
    private Long id;
    private String venueName;
    private String location;
    private double PricePerHour;
    private Long owner;


}
