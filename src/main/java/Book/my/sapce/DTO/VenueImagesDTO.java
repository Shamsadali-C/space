package Book.my.sapce.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VenueImagesDTO {

//    @NotNull(message = "Venue ID is required")
//    @JoinColumn(name = "venue_id")
    private Long venueId;

//    @NotBlank(message = "Image URL cannot be empty")
    private String imageUrl;



}
