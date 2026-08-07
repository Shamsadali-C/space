package Book.my.sapce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Table(name = "booking")
//@Table(uniqueConstraints = @UniqueConstraint(
//        columnNames ={
//                "venueId",
//                "booking_time",
//                "booking_date"
//        }
//        ))
@Entity
public class Booking {

    @Id
    @GeneratedValue
    public Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="venue_id")
    private Venue venue;

    private String bookingStatus;

    private LocalDate date;

    private LocalTime time;





    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
