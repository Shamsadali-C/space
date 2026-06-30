package Book.my.sapce.Service;

import Book.my.sapce.Model.Venue;
import Book.my.sapce.Repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    @Autowired
    public VenueRepository venueRepository;

    @PostMapping
    public Venue save(Venue venue){
        return venueRepository.save(venue);
    }
    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }
    public Venue updateVenue(Long id, Venue newVenue) {

        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        venue.setVenueName(newVenue.getVenueName());
        venue.setLocation(newVenue.getLocation());
        venue.setPricePerHour(newVenue.getPricePerHour());


        return venueRepository.save(venue);
    }
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

    public List<Venue> getVenuesByOwner(Long ownerId) {
        return venueRepository.getVenueByOwner(ownerId);
    }
//    public Venue createVenue(Long ownerId, Venue venue) {
//
//        Owner owner = venueRepository.findById(ownerId)
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//        venue.setOwner(owner);
//
//        return venueRepository.save(venue);
//    }


    public Optional<Venue> getVenuesById(Long id) {
        return venueRepository.findById(id);
    }
}

