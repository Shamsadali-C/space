package Book.my.sapce.DTO;

import jakarta.validation.constraints.NotBlank;

public class VenueRequest {
    @NotBlank(message = "Venue Name Is Required")
    private String venueName ;

    private String location  ;

    private double Price ;


}
