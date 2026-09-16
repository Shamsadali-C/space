package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueRequestDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final UserRepository userRepository;
    private final BookingService bookingService;

    public VenueService(
            VenueRepository venueRepository,
            UserRepository userRepository,
            BookingService bookingService) {

        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
        this.bookingService = bookingService;
    }

    public Venue addVenue(VenueRequestDTO venueRequest) {


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();


        User owner = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Owner not found")
                );

        Venue venue = new Venue();

        venue.setVenueName(venueRequest.getVenueName());
        venue.setLocation(venueRequest.getLocation());
        venue.setCapacity(venueRequest.getCapacity());
        venue.setPrice(venueRequest.getPrice());

        venue.setOwner(owner);

        venue.setStatus(VenueStatus.AVAILABLE);

        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenue() {
        return venueRepository.findAll();
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }




    public ResponseEntity<Venue> getVenueById(Long id) {
        Optional<Venue> venue = venueRepository.findById(id);

        return venue.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    public Venue updateVenue(Long id, Venue venueDetails) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        venue.setVenueName(venueDetails.getVenueName());
        venue.setLocation(venueDetails.getLocation());
        venue.setPrice(venueDetails.getPrice());
        venue.setOwner(venueDetails.getOwner());
//        venue.setAvailableStatus(venueDetails.isAvailableStatus());

        return venueRepository.save(venue);
    }

    @Transactional
    public Venue maintenance(Long venueId, LocalDate date) throws Exception {

        Venue v = venueRepository.findById(venueId)
                .orElseThrow(() ->
                        new RuntimeException("Venue not found")
                );
        v.setStatus(VenueStatus.MAINTENANCE);
        Venue savedVenue = venueRepository.save(v);
        bookingService.cancelBookingsForVenueDate(venueId, date);

        return savedVenue;
    }

    @Transactional
    public Venue holiday(Long venueId, LocalDate date) throws Exception {

        Venue v = venueRepository.findById(venueId)
                .orElseThrow(() ->new RuntimeException("Venue not found"));
              v.setStatus(VenueStatus.HOLIDAY);
              Venue savedVenue = venueRepository.save(v);
        bookingService.cancelBookingsForVenueDate(venueId,date);

        return savedVenue;
    }

    public Venue available(Long venueId){
        Venue v=venueRepository.findById(venueId)
                .orElseThrow(()->new RuntimeException("venue not found"));
        v.setStatus(VenueStatus.AVAILABLE);
        return venueRepository.save(v);
    }

    public List<Venue> getOwnerVenues(String username) {

        User owner = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Owner not found")
                );

        return venueRepository.findByOwner(owner);
    }


}
