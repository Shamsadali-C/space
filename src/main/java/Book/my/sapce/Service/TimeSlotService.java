package Book.my.sapce.Service;

import Book.my.sapce.DTO.TimeSlotRequestDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.SlotDurationRepository;
import Book.my.sapce.Repository.TimeSlotRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final SlotDurationRepository slotDurationRepository;


    public TimeSlotService(TimeSlotRepository timeSlotRepository,
                           VenueRepository venueRepository,
                           UserRepository userRepository,
                           SlotDurationRepository slotDurationRepository) {

        this.timeSlotRepository = timeSlotRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
        this.slotDurationRepository=slotDurationRepository;
    }


    @Transactional
    public List<TimeSlot> createSlot( Long venueId,
                                      TimeSlotRequestDTO request,
                                      String username) {


        Venue venue =venueRepository.findById(venueId)
                        .orElseThrow(() ->new RuntimeException("Venue not found"));

        User owner =userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Owner not found"));


        if (!venue.getOwner().getId().equals(owner.getId())) {
            throw new RuntimeException("You don't own this venue");
        }


        if (request.getSlotDate() == null) {
            throw new RuntimeException("Date is required");
        }

        if (request.getStartTime() == null) {
            throw new RuntimeException("Start time is required");
        }


        if (request.getEndTime() == null) {
            throw new RuntimeException("End time is required");
        }


        if (request.getDuration() == null || request.getDuration() <= 0) {
            throw new RuntimeException("Duration is required");
        }


        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        SlotDuration duration=slotDurationRepository.findByDurationMinutesAndActiveTrue(request.getDuration())
                .orElseThrow(()-> new RuntimeException("the slot is not allowed"));

        int durationMinutes= duration.getDurationMinutes();

        long totalMinutes=Duration.between(request.getStartTime(), request.getEndTime()).toMinutes();

        if(totalMinutes % durationMinutes !=0){
            throw  new RuntimeException("start time and endtime must be divisible by the duration");

        }
//        long totalMinutes =Duration.between(request.getStartTime(),request.getEndTime()).toMinutes();
//
//        if (totalMinutes % 60 != 0) {
//            throw new RuntimeException( "Start and end time must form complete 60 minute slots");
//        }

        boolean overlap =timeSlotRepository.existsOverlappingSlot(
                                venueId,
                                request.getSlotDate(),
                                request.getStartTime(),
                                request.getEndTime()
                        );


        if (overlap) {
            throw new RuntimeException("This time period overlaps with an existing slot");
        }


        List<TimeSlot> slots =new ArrayList<>();
        LocalTime currentStart =request.getStartTime();

        while (currentStart.isBefore(request.getEndTime())) {


            LocalTime currentEnd =currentStart.plusMinutes(durationMinutes);

            TimeSlot slot =new TimeSlot();

            slot.setVenue(venue);
            slot.setSlotDate(request.getSlotDate());
            slot.setStartTime(currentStart);
            slot.setEndTime(currentEnd);
            slot.setDuration(durationMinutes);
            slot.setStatus( TimeSlotStatus.AVAILABLE);
            slots.add(slot);
            currentStart =currentEnd;
        }


        return timeSlotRepository.saveAll(slots);
    }


    public List<TimeSlot> getSlots( Long venueId,LocalDate date) {

        return timeSlotRepository.findByVenueIdAndSlotDate( venueId, date);
    }
}