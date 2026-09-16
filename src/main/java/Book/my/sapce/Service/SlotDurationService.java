package Book.my.sapce.Service;

import Book.my.sapce.Model.SlotDuration;
import Book.my.sapce.Repository.SlotDurationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotDurationService {


    private final SlotDurationRepository slotDurationRepository;

    public SlotDurationService(
           SlotDurationRepository slotDurationRepository) {

        this.slotDurationRepository = slotDurationRepository;
    }

    public List<SlotDuration> getAllDurations() {

        return slotDurationRepository
                .findAll()
                .stream()
                .sorted((a, b) ->
                        a.getDurationMinutes()
                                .compareTo(b.getDurationMinutes()))
                .toList();
    }

    // Get only active durations for Owner
    public List<SlotDuration> getActiveDurations() {

        return slotDurationRepository
                .findByActiveTrueOrderByDurationMinutesAsc();
    }

    // Add duration
    public SlotDuration addDuration(
            Integer durationMinutes) {

        if (durationMinutes == null ||durationMinutes <= 0) {

            throw new RuntimeException("Duration must be greater than 0 minutes");
        }

        if (slotDurationRepository.existsByDurationMinutes(
                durationMinutes)) {

            throw new RuntimeException(
                    "This duration already exists");
        }

       SlotDuration option = new SlotDuration();

        option.setDurationMinutes(durationMinutes);
        option.setActive(true);

        return slotDurationRepository.save(option);
    }

    public SlotDuration toggleDuration(Long id) {

       SlotDuration option =
               slotDurationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Duration not found"));

        option.setActive(!option.isActive());

        return slotDurationRepository.save(option);
    }

    public void deleteDuration(Long id) {

       SlotDuration option =
               slotDurationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Duration not found"));

        slotDurationRepository.delete(option);
    }
}

