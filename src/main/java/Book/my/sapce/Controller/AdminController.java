package Book.my.sapce.Controller;

import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserRepository userRepository;
    private VenueRepository venueRepository;
    private BookingRepository bookingRepository;

    public AdminController(UserRepository userRepository,
                           VenueRepository venueRepository,
                           BookingRepository bookingRepository){

        this.userRepository=userRepository;
        this.venueRepository=venueRepository;
        this.bookingRepository=bookingRepository;
    }

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


    @PutMapping("/users/{id}/make-owner")
    public ResponseEntity<?> makeOwner(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.OWNER);
        userRepository.save(user);

        return ResponseEntity.ok("User promoted to owner successfully");
    }

    @PutMapping("/users/{id}/make-user") public ResponseEntity<?> makeUser( @PathVariable Long id) {
        User user = userRepository .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.USER);
        userRepository.save(user);
        return ResponseEntity.ok( "Owner role changed to USER successfully" );
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/venues")
    public List<Venue> getVenue() {
        return venueRepository.findAll();
    }

    @GetMapping("/bookings")
    public List<Booking> getBooking() {
        return bookingRepository.findAll();

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }



    @DeleteMapping("/venues/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id) {
       Venue venue=venueRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Venue not found"));

       venueRepository.delete(venue);

       return ResponseEntity.ok("Venue deleted successfully");
    }



    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        Booking booking=bookingRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Booking Not Fonud"));

        return ResponseEntity.ok("Booking deleted successfully");
    }

}
