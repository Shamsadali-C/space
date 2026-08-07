package Book.my.sapce.Service;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.Model.Booking;
import Book.my.sapce.Model.BookingStatus;
import Book.my.sapce.Model.User;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {


    @Autowired
    public BookingRepository bookingRepository;
    @Autowired
    public VenueRepository venueRepository;
    @Autowired
    public UserRepository userRepository;


    public Booking CreateBooking (Long UserId ,Long VenueId) {

     User user = userRepository.findById(UserId)
             .orElseThrow(()->new RuntimeException("User Does not exist"));
     Venue venue = venueRepository.findById(VenueId)
             .orElseThrow(() ->new RuntimeException("Venue doesn't Exist"));

     Booking booking = new Booking();

     booking.setUser(user);
     booking.setVenue(venue);
     booking.setBookingStatus("PENDING");
     booking.setDate(LocalDate.now());
     booking.setTime(LocalTime.now());

     return bookingRepository.save(booking);
 }
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }


    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingDetailsDTO getBookingDetails(Long bookingId, User user) {

        Booking booking = bookingRepository.findByIdAndUserId(bookingId, user.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingDetailsDTO dto = new BookingDetailsDTO();
        dto.setBookingId(booking.getId());
        dto.setTime(booking.getTime());
        dto.setDate(booking.getDate());
        dto.setBookingStatus(booking.getBookingStatus());

        return dto;
    }

    public Booking approve(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));

      booking.setBookingStatus(BookingStatus.ACCEPTED.name());

        return bookingRepository.save(booking);
    }

    public Booking cancel(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED.name());

        return bookingRepository.save(booking);
    }

    public Booking reject(Long bookingId) {
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.REJECTED.name());

        return bookingRepository.save(booking);
    }


}