package Book.my.sapce.Service;

import Book.my.sapce.DTO.TimeSlotRequestDTO;
import Book.my.sapce.Model.TimeSlot;
import Book.my.sapce.Model.TimeSlotStatus;
import Book.my.sapce.Model.User;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.TimeSlotRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    public TimeSlotService(
            TimeSlotRepository timeSlotRepository,
            VenueRepository venueRepository,
            UserRepository userRepository) {

        this.timeSlotRepository = timeSlotRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TimeSlot createSlot(
            Long venueId,
            TimeSlotRequestDTO request,
            String username) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Venue not found"
                        ));

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Owner not found"
                        ));

        if (!venue.getOwner().getId()
                .equals(owner.getId())) {

            throw new RuntimeException(
                    "You don't own this venue"
            );
        }

        boolean exists =
                timeSlotRepository
                        .existsByVenueIdAndSlotDateAndStartTimeAndEndTime(
                                venueId,
                                request.getSlotDate(),
                                request.getStartTime(),
                                request.getEndTime()
                        );

        if (exists) {

            throw new RuntimeException(
                    "This time slot already exists"
            );
        }

        TimeSlot slot = new TimeSlot();

        slot.setVenue(venue);
        slot.setSlotDate(request.getSlotDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setStatus(TimeSlotStatus.AVAILABLE);

        return timeSlotRepository.save(slot);
    }

    public List<TimeSlot> getSlots(
            Long venueId,
            LocalDate date) {

        return timeSlotRepository
                .findByVenueIdAndSlotDate(
                        venueId,
                        date
                );
    }
}