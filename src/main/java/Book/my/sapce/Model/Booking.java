package Book.my.sapce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
        columnNames ={
                "venue_Id",
                "date",
                "time"
        }
        ))

public class Booking {

    @Id
    @GeneratedValue
    public Long id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="venue_id",nullable = false)
    private Venue venue;

    private String bookingStatus;

    private LocalDate date;

    private LocalTime time;




}
