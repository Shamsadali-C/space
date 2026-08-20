package Book.my.sapce.Service;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.TimeSlotRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {



    private final BookingRepository bookingRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;


    @Transactional
    public Booking createBooking(
            Long userId,
            Long slotId) {

        TimeSlot slot = timeSlotRepository
                .findById(slotId)
                .orElseThrow(() -> new RuntimeException("Time slot not found"));

        if (slot.getStatus() != TimeSlotStatus.AVAILABLE) {

            throw new RuntimeException("This time slot is not available");
        }

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found" ));

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setVenue(slot.getVenue());
        booking.setTimeSlot(slot);
        booking.setBookingStatus("PENDING");

        slot.setStatus(TimeSlotStatus.PENDING);

        timeSlotRepository.save(slot);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }


    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Booking not found"));

        bookingRepository.delete(booking);
    }

    public BookingService(BookingRepository bookingRepository,
                          VenueRepository venueRepository,
                          UserRepository userRepository,
                          TimeSlotRepository timeSlotRepository) {

        this.bookingRepository = bookingRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public BookingDetailsDTO getBookingDetails(Long bookingId, User user) {

        Booking booking = bookingRepository.findByIdAndUserId(bookingId, user.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingDetailsDTO dto = new BookingDetailsDTO();
        dto.setBookingId(booking.getId());
//        dto.setTime(booking.getTime());
//        dto.setDate(booking.getDate());
        dto.setBookingStatus(booking.getBookingStatus());

        return dto;
    }

    @Transactional
    public Booking approve(Long bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.ACCEPTED.name());

        TimeSlot slot = booking.getTimeSlot();

        slot.setStatus(TimeSlotStatus.BOOKED);

        timeSlotRepository.save(slot);

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

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new RuntimeException( "Booking not found"));

        booking.setBookingStatus(BookingStatus.REJECTED.name() );

        TimeSlot slot = booking.getTimeSlot();

        slot.setStatus(TimeSlotStatus.AVAILABLE);

        timeSlotRepository.save(slot);

        return bookingRepository.save(booking);
    }

    public List<Booking> getOwnerBookings(String username) {

        return bookingRepository.findByVenueOwnerUsername(username);
    }

    public List<Booking> getUserBookings(Long userId) {

        return bookingRepository.findByUserId(userId);
    }


}