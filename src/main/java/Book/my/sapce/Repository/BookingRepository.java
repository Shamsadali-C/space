package Book.my.sapce.Repository;

import Book.my.sapce.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);

    long countByBookingStatus(String bookingStatus);

    List<Booking> findByVenueOwnerUsername(String username);
    List<Booking> findByUserId(Long userId);

    boolean existsByVenue_IdAndDateAndTime(Long venueId,
                                           LocalDate bookingDate,
                                           LocalTime bookingTime);
}
