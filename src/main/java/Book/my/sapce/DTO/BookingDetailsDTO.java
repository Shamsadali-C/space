package Book.my.sapce.DTO;

import Book.my.sapce.Model.BookingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Data
public class BookingDetailsDTO {

        private Long bookingId;

        private BookingStatus bookingStatus;

}
