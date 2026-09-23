package Book.my.sapce.Repository;

import Book.my.sapce.Model.VenueBlockedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VenueBlockedDateRepository
        extends JpaRepository<VenueBlockedDate, Long> {

    Optional<VenueBlockedDate> findByVenueIdAndBlockedDate(
            Long venueId,
            LocalDate blockedDate
    );


}