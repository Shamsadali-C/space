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

    @NotBlank(message="Must Enter The Venue Name")
    private String venueName;

    @NotBlank(message="Must Enter The Location Name")
    private String location;


    @NotNull(message = "Must Enter The Price" )
    private Double price;


    @ManyToOne
    @JoinColumn(name = "owner_id",updatable=false)
    private Long owner;

    public void setowner(Long owner) {
        this.owner = owner;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public void setvenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public void setprice(Double price) {
        this.price = price;
    }

    public void setOwnerId(Long ownerId) {
    }



}
