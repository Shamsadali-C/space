package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="venue")
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

    @Getter
    @ManyToOne
    @JoinColumn(name = "owner_id",updatable=false)
    private User owner;


    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }




    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
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



    //    public void setowner(Long owner) {
//        this.owner = owner;
//    }
//
//    public void setid(Long id) {
//        this.id = id;
//    }
//
//    public void setvenueName(String venueName) {
//        this.venueName = venueName;
//    }
//
//    public void setlocation(String location) {
//        this.location = location;
//    }
//
//    public void setprice(Double price) {
//        this.price = price;
//    }
//
//    public void setOwnerId(Long ownerId) {
//    }



}
