package Book.my.sapce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VenueImagesDTO {

    @NotNull(message = "Venue ID is required")
    private Long venueId;

    @NotBlank(message = "Image URL cannot be empty")
    private String imageUrl;

    @NotBlank(message = "Image name cannot be empty")
    private String imageName;

    @NotBlank(message = "Image type cannot be empty")
    private String imageType;
}
