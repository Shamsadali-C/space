package Book.my.sapce.Repository;

import Book.my.sapce.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository<Booking, Long> {

    long countByBookingStatus(String BookingStatus);

}
