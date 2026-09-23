package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Table(name = "venue_images")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class   VenueImages {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

}
