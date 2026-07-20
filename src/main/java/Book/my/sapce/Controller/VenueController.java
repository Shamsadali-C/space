package Book.my.sapce.Controller;

import Book.my.sapce.DTO.VenueRequest;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Service.VenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/venue")
public class VenueController {

    @Autowired
    private VenueService venueService;

    
    @PostMapping("/add")
    public Venue addVenue(@Valid @RequestBody VenueRequest venueRequest, List<MultipartFile> imageFiles) {
        return venueService.addVenue(venueRequest);
    }

    @GetMapping
    public List<Venue> getVenue() {
        return venueService.getAllVenue();
    }

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
