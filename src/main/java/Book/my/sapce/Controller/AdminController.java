package Book.my.sapce.Controller;

import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> dashboard() {
        Map<String, Long> map = new HashMap<>();
        map.put("totalUsers", userRepository.count());
        map.put("totalVenues", venueRepository.count());
        map.put("totalBookings", bookingRepository.count());

        map.put("users", userRepository.countByRole(Role.USER));
        map.put("owners", userRepository.countByRole(Role.OWNER));
        map.put("admins", userRepository.countByRole(Role.ADMIN));

        map.put("pendingBookings", bookingRepository.countByBookingStatus("PENDING"));
        map.put("rejectedBookings", bookingRepository.countByBookingStatus("REJECTED"));
        map.put("acceptedBookings", bookingRepository.countByBookingStatus("ACCEPTED"));
        map.put("cancelledBookings", bookingRepository.countByBookingStatus("CANCELLED"));

        return ResponseEntity.ok(map);
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @GetMapping("/venues")
    public List<Venue> getVenue() {
        return venueRepository.findAll();
    }

    @DeleteMapping("venues/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueRepository.deleteById(id);
        return "venue deleted successfully";
    }

    @GetMapping("/bookings")
    public List<Booking> getBooking() {
        return bookingRepository.findAll();

    }

    @DeleteMapping("/bookings/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }

}
