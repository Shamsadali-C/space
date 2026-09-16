package Book.my.sapce.Repository;

import Book.my.sapce.Model.SlotDuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlotDurationRepository extends JpaRepository<SlotDuration, Long> {

    List<SlotDuration>
    findByActiveTrueOrderByDurationMinutesAsc();

    Optional<SlotDuration>
    findByDurationMinutesAndActiveTrue(Integer durationMinutes);

    boolean existsByDurationMinutes(Integer durationMinutes);
}