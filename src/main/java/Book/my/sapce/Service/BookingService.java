package Book.my.sapce.Service;

import Book.my.sapce.Model.Booking;
import Book.my.sapce.Model.User;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {


    @Autowired
    public BookingRepository bookingRepository;
    @Autowired
    public VenueRepository venueRepository;
    @Autowired
    public UserRepository userRepository;

 @PostMapping("{id}/{Venueid}")
    public Booking CreateBooking (Long UserId ,Long VenueId) {

     User user = userRepository.findById(UserId)
             .orElseThrow();
     Venue venue = venueRepository.findById(VenueId)
             .orElseThrow();

     Booking booking = new Booking();

     booking.setUser(user);
     booking.setVenue(venue);
     booking.setStatus("PENDING");
     booking.setDate(LocalDate.now());
     return bookingRepository.save(booking);
 }
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }


    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

}