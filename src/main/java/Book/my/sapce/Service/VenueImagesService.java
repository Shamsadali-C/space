package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Repository.VenueImagesRepository;
import Book.my.sapce.Repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueImagesService {


    public final VenueImagesRepository venueImagesRepository;
    public final VenueRepository venueRepository;


    public VenueImages addImage(VenueImagesDTO dto) {

        // Check venue exists
        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found!"));

        // Max 3 image limit check
        Long imageCount = venueImagesRepository.countByVenueId(dto.getVenueId());
        if (imageCount >= 3) {
            throw new RuntimeException("Maximum 3 images allowed per venue! " + "Please delete an existing image first.");
        }

        VenueImages image = new VenueImages();
        image.setImageUrl(dto.getImageUrl());
        image.setImageName(dto.getImageName());
        image.setImageType(dto.getImageType());
        image.setVenue(venue);

        return venueImagesRepository.save(image);
    }
    public List<VenueImages>  getImagesByVenue(Long venueId) {

        return venueImagesRepository.findByVenueId(venueId);
    }

    public List<VenueImages> getAllImages() {
        return venueImagesRepository.findAll();
    }



}
