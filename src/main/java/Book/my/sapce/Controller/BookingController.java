package Book.my.sapce.Controller;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.Model.Booking;
import Book.my.sapce.Model.User;
import Book.my.sapce.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @PostMapping("/{userId}/{venueId}")
    public Booking CreateBooking(@PathVariable Long userId, @PathVariable Long venueId) {
        try {
            return bookingService.CreateBooking(userId, venueId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<Booking> getBooking() {
        return bookingService.getAllBooking();


    }
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking deleted successfully";
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDetailsDTO> getBookingDetails(
            @PathVariable Long bookingId, @RequestParam Long userId) {

        User user = new User();
        user.setId(userId);

        BookingDetailsDTO booking = bookingService.getBookingDetails(bookingId, user);

        return ResponseEntity.ok(booking);
    }
}
