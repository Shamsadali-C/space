package Book.my.sapce.Service;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Transactional
    public Booking CreateBooking(
            Long userId,
            Long venueId,
            LocalDate bookingDate,
            LocalTime bookingTime) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new RuntimeException("Venue doesn't exist"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User doesn't exist"));

        boolean alreadyBooked =
                bookingRepository.existsByVenue_IdAndDateAndTime(
                        venueId,
                        bookingDate,
                        bookingTime
                );

        if (alreadyBooked) {
            throw new RuntimeException(
                    "This time slot is already booked"
            );
        }

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setVenue(venue);
        booking.setDate(bookingDate);
        booking.setTime(bookingTime);
        booking.setBookingStatus("PENDING");

        return bookingRepository.save(booking);
    }


    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }


    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        bookingRepository.delete(booking);
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

//    public Booking cancel(Long bookingId) {
//        Booking booking=bookingRepository.findById(bookingId)
//                .orElseThrow(()->new RuntimeException("Booking not found"));
//
//        booking.setBookingStatus(BookingStatus.CANCELLED.name());
//
//        return bookingRepository.save(booking);
//    }

    @Transactional
    public Booking reject(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        booking.setBookingStatus(
                BookingStatus.REJECTED.name()
        );

        Venue venue = booking.getVenue();

        venue.setStatus(VenueStatus.AVAILABLE);

        venueRepository.save(venue);

        return bookingRepository.save(booking);
    }

    public List<Booking> getOwnerBookings(String username) {

        return bookingRepository.findByVenueOwnerUsername(username);
    }

    public List<Booking> getUserBookings(Long userId) {

        return bookingRepository.findByUserId(userId);
    }


}