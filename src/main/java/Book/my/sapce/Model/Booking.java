package Book.my.sapce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
//@Table(name = "booking")
@Table(
        uniqueConstraints = @UniqueConstraint(
        columnNames ={
                "venueId",
                "booking_time",
                "booking_date"
        }
        ))
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



}
