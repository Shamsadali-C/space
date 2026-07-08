package Book.my.sapce.Controller;

import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import Book.my.sapce.Service.VenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;
    private VenueRepository venueRepository;
    private UserRepository userRepository;

    @PostMapping("/add")
    public Venue addVenue(@Valid @RequestBody Venue venue) {
        return venueService.save(venue);
    }
//    @PostMapping("/venues")
//    public Venue addVenue( @PathVariable Long ownerId,
//                      @RequestBody Venue venue) {
//
//      User owner = userRepository.findById(ownerId)
//            .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//      venue.setOwner(owner);
//
//       return venueRepository.save(venue);
//    }


    @GetMapping
    public List<Venue> getVenue() {

        return venueService.getAllVenue();
    }


    @PutMapping("/venues/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venueDetails) {
        Venue venue = venueRepository.findById(id).orElseThrow();
//        venue.setLocation(venueDetails.getLocation());
//        venue.setPrice(venueDetails.getPrice());
//        venue.setVenueName(venueDetails.getVenueName());
//        venue.setOwner(getOwner());
        return venueRepository.save(venue);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "venue deleted successfully";
    }

}
