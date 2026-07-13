package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Table(name = "venue_images")
@Data
public class VenueImages {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    public Long id;

    private String imageUrl;

    private String imageName;

    private String imageType;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

}
