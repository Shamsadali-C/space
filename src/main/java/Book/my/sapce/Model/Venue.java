package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Venue {

    @Getter
    @Id
    @GeneratedValue
    private Long id;
    @Getter
    @NotBlank(message="Must Enter The Venue Name")
    private String venueName;

    @Getter
    @NotBlank(message="Must Enter The Location Name")
    private String location;

    @Getter
    @NotNull(message = "Must Enter The Price" )
    private Double price;


    @ManyToOne
    @JoinColumn(name = "owner_id",updatable=false)
    private Long owner;

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOwnerId(Long ownerId) {
    }



}
