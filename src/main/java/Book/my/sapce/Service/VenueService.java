package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueRequest;
import Book.my.sapce.DTO.VenueResponce;
import Book.my.sapce.Model.Venue;
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


    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateOwner(Long venueId, Long ownerId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        venue.setOwnerId(ownerId);
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenue() {
        return venueRepository.findAll();
    }

    public Venue updateVenue(Long id, Venue newVenue) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        venue.setVenueName(newVenue.getVenueName());
        venue.setLocation(newVenue.getLocation());
        venue.setPrice(newVenue.getPrice());


        return venueRepository.save(venue);
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


//
//    public VenueResponce createVenue(VenueRequest request, Long userId) {
//        Venue venue = Venue.builder()
//                .venueName(request.getvenueName())
//                .location(request.getLocation())
//                .price(request.getPrice())
//                .ownerId(userId)
//                .build();
//
//        Venue saved=VenueRepository.save(venue)
//                return maptoresponce(saved);
//        }
//    }


}
