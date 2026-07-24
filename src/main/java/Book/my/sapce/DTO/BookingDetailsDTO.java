package Book.my.sapce.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDetailsDTO {

    private Long BookingId;
    private LocalTime Time;
    private LocalDate Date;
    private String BookingStatus;


}
