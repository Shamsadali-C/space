package Book.my.sapce.Service;

import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    public VenueRepository venueRepository;


    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenue() {
        return venueRepository.findAll();
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }


//
//    public Optional<Venue> getVenuesById(Long id) {
//        return venueRepository.findById(id);
//    }

    public ResponseEntity<Venue> getVenueById(Long id) {
        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            return new ResponseEntity<>(venue.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public Venue updateVenue(Long id, Venue venueDetails) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        venue.setVenueName(venueDetails.getVenueName());
        venue.setLocation(venueDetails.getLocation());
        venue.setPrice(venueDetails.getPrice());
        venue.setOwner(venueDetails.getOwner());

        return venueRepository.save(venue);
    }






}
