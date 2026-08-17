package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.awt.image.BufferedImage;

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



    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenueStatus status=VenueStatus.AVAILABLE;

//    public boolean isAvailableStatus() {
//        return availableStatus;
//    }
//
//    public void setAvailableStatus(boolean availableStatus) {
//        this.availableStatus = availableStatus;
//    }


    }
