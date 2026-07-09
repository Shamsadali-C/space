package Book.my.sapce.Controller;

import Book.my.sapce.Model.Booking;
import Book.my.sapce.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @PostMapping("/{userId}/{venueId}")
    public Booking CreateBooking(@PathVariable Long userId, @PathVariable Long venueId) {
        return bookingService.CreateBooking(userId, venueId);
    }

    @GetMapping
    public List<Booking> getBooking() {
        return bookingService.getAllBooking();


    }
}
