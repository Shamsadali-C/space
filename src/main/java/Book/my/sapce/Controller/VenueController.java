package Book.my.sapce.Controller;

import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.VenueRepository;
import Book.my.sapce.Service.VenueService;
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

    @PostMapping("/add")
    public Venue addVenue(@RequestBody Venue venue) {
        return venueService.save(venue);
    }

    @GetMapping
    public List<Venue> getVenue() {
        return venueService.getAllVenue();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Venue>> getVenuesByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(venueService.getVenuesByOwner(ownerId));
    }
    @PutMapping("/venues/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venueDetails) {
        Venue venue = venueRepository.findById(id).orElseThrow();
        venue.setLocation(venueDetails.getLocation());
        venue.setPricePerHour(venueDetails.getPricePerHour());
        venue.setVenueName(venueDetails.getVenueName());
        return venueRepository.save(venue);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
//        return venueService.getVenueById(id);
//    }
    @DeleteMapping("/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "venue deleted successfully";
    }
//    @PostMapping("/owner/{ownerId}/venue")
//    public Venue createVenue(@PathVariable Long ownerId,
//                             @RequestBody Venue venue) {
//
//        return venueService.createVenue(ownerId, venue);
//    }



}
