package Book.my.sapce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TimeSlotRequestDTO {

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
