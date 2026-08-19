package Book.my.sapce.Repository;

import Book.my.sapce.Model.TimeSlot;
import Book.my.sapce.Model.TimeSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository
        extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByVenueIdAndSlotDate(
            Long venueId,
            LocalDate slotDate
    );

    Optional<TimeSlot> findByVenueIdAndSlotDateAndStartTimeAndEndTime(
            Long venueId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime
    );

    boolean existsByVenueIdAndSlotDateAndStartTimeAndEndTime(
            Long venueId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime
    );
}