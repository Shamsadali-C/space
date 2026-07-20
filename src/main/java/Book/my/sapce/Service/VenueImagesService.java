package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Repository.VenueImagesRepository;
import Book.my.sapce.Repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VenueImagesService {


    public final VenueImagesRepository venueImagesRepository;
    public final VenueRepository venueRepository;


    public void uploadfile(VenueImagesDTO dto, List<MultipartFile> file) {


        Venue venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found!"));


        Long imageCount = venueImagesRepository.countByVenueId(dto.getVenueId());
        if (imageCount + file.size() >= 3) {
            throw new RuntimeException("Maximum 3 images allowed per venue! " + "Please delete an existing image first.");
        }// limit set cheythath 3
        List<VenueImages> venueImagesList= new ArrayList<>();
        for(MultipartFile image:file){
            int extensionIndex =image.getOriginalFilename().lastIndexOf('.');
            String extension = image.getOriginalFilename().substring(extensionIndex);
            String imageUrl = UUID.randomUUID()+extension;

            VenueImages images1 = VenueImages.builder()
                    .imageUrl(imageUrl)
                    .venue(venue)
                    .build();

            venueImagesList.add(images1);

        }

        venueImagesRepository.saveAll(venueImagesList);

//        for(VenueImages venueImage :venueImagesList){
//
//            venueImagesRepository.save(venueImage);
//
//        }

    }
    public List<VenueImages>  getImagesByVenue(Long venueId) {

        return venueImagesRepository.findByVenueId(venueId);
    }

    public List<VenueImages> getAllImages() {
        return venueImagesRepository.findAll();
    }


    public void deleteVenueId(Long venueId) {venueImagesRepository.deleteById(venueId);
    }
}
