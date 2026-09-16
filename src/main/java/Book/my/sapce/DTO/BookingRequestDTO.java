package Book.my.sapce.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDTO {

    private List<Long> slotIds;
}