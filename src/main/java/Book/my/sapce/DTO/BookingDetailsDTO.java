package Book.my.sapce.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Data
public class BookingDetailsDTO {

    private Long bookingId;
    private LocalTime time;
    private LocalDate date;
    private String bookingStatus;


}
