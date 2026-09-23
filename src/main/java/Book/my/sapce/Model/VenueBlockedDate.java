package Book.my.sapce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "venue_blocked_date",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"venue_id", "blocked_date"}
                )
        }
)
@Getter
@Setter
public class VenueBlockedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "blocked_date", nullable = false)
    private LocalDate blockedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenueStatus status;
}