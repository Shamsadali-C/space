package Book.my.sapce.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "venue")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venue {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message="Must Enter The Venue Name")
    private String venueName;

    @NotBlank(message="Must Enter The Location Name")
    private String location;

    @NotNull
    private Integer capacity;

    @NotNull(message = "Must Enter The Price" )
    private Double price;


    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    @OneToMany(mappedBy = "venue")
    @JsonManagedReference
    private List<TimeSlot> timeSlots;



    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenueStatus status=VenueStatus.AVAILABLE;



    }
