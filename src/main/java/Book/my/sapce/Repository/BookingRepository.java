package Book.my.sapce.Repository;

import Book.my.sapce.Model.Booking;
import Book.my.sapce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {

    long countByBookingStatus(String BookingStatus);
    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);

}
