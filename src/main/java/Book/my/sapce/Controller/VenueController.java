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


    public VenueController(VenueService venueService){
        this.venueService=venueService;
    }

    @PostMapping("/add")
    public Venue addVenue(@Valid @RequestBody Venue venue) {
        return venueService.addVenue(venue);
    }

    @GetMapping
    public List<Venue> getVenue() {

        return venueService.getAllVenue();
    }

    @PutMapping("/venues/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
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
