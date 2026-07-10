package Book.my.sapce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="venue")
@AllArgsConstructor
@NoArgsConstructor
public class Venue {

    @Getter
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Getter
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

    private boolean availableStatus;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(boolean availableStatus) {
        this.availableStatus = availableStatus;
    }






}
