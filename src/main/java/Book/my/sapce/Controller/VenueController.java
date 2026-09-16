package Book.my.sapce.Controller;

import Book.my.sapce.DTO.VenueRequestDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Service.VenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add")
    public Venue addVenue(@Valid @RequestBody VenueRequestDTO venueRequest) {
        return venueService.addVenue(venueRequest);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/maintenance/{venueId}")
    public ResponseEntity<?> maintenance(@PathVariable Long venueId,@RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(venueService.maintenance(venueId, date));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/holiday/{venueId}")
    public ResponseEntity<?> holiday(@PathVariable Long venueId,@RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(venueService.holiday(venueId, date));
    }

//    @GetMapping
//    public List<Venue> getVenue() {
//        return venueService.getAllVenue();
//    }

    @PutMapping("/venues/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        venue.setId(id);
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
