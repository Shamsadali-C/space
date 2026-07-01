package Book.my.sapce.Model;

import jakarta.persistence.*;

@Entity
public class Venue {

    @Id
    @GeneratedValue
    private Long id;

    private String venueName;

    private String location;

    private Double pricePerHour;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;



    public User getOwner() {
        return owner;
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

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

}
