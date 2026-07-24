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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
