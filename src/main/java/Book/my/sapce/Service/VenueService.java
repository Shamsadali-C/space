package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueRequest;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    public VenueRepository venueRepository;

    @Autowired
    public UserRepository userRepository;


    public Venue addVenue(VenueRequest venueRequest) {
//        User user = userRepository.findById(Integer.toUnsignedLong(1)).orElseThrow(()->new RuntimeException("NO User"));
        Venue venue = Venue.builder()
                .venueName(venueRequest.getVenueName())
                .availableStatus(true)
                .capacity(venueRequest.getCapacity())
                .location(venueRequest.getLocation())
                .price(venueRequest.getPrice())
//                .owner(user)
                .build();

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
        venue.setAvailableStatus(venueDetails.isAvailableStatus());

        return venueRepository.save(venue);
    }






}
