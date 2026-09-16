package Book.my.sapce.Repository;


import Book.my.sapce.Model.TimeSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByVenueIdAndSlotDate(
            Long venueId,
            LocalDate slotDate
    );

//    Optional<TimeSlot> findByVenueIdAndSlotDateAndStartTimeAndEndTime(
//            Long venueId,
//            LocalDate slotDate,
//            LocalTime startTime,
//            LocalTime endTime
//    );

    @Query("""
        SELECT COUNT(t) > 0
        FROM TimeSlot t
        WHERE t.venue.id = :venueId
        AND t.slotDate = :date
        AND t.startTime < :endTime
        AND t.endTime > :startTime
    """)
    boolean existsOverlappingSlot(
            @Param("venueId") Long venueId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT t
        FROM TimeSlot t
        WHERE t.id = :id
    """)
//    Optional<TimeSlot> findByIdForUpdate(
//            @Param("id") Long id
//    );

    boolean existsByVenueIdAndSlotDateAndStartTimeAndEndTime(
            Long venueId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime
    );
}