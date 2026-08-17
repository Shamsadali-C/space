package Book.my.sapce.Service;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Repository.VenueImagesRepository;
import Book.my.sapce.Repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VenueImagesService {


    public final VenueImagesRepository venueImagesRepository;
    public final VenueRepository venueRepository;

    public static final String FILE_UPLOAD_DIR = "C:\\Users\\Shamsadali\\OneDrive\\intership\\upload\\";


    public void uploadfile(Long venueId, List<MultipartFile> file) {

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found!"+venueId));


        Long imageCount = venueImagesRepository.countByVenueId(venueId);
        if (imageCount + file.size() > 3) {
            throw new RuntimeException("Maximum 3 images allowed per venue!. ");
        }                                                                          // limit set cheythath 3

        File dir = new File(FILE_UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<VenueImages> venueImagesList = new ArrayList<>();
        for (MultipartFile image : file) {
            if (image.isEmpty()) {
                continue;
            }

            String contentType = image.getContentType();

            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            String originalName = image.getOriginalFilename();
//            int index = originalName.lastIndexOf(".");
//            String extension = originalName.substring(-1);

            if (originalName == null || !originalName.contains(".")) {
                throw new RuntimeException("Invalid image file");
            }

            String extension = originalName.substring(
                    originalName.lastIndexOf(".")
            );

            String imageName =
                    UUID.randomUUID()+ extension;

            try {

                File destination = new File(FILE_UPLOAD_DIR, imageName);

                image.transferTo(destination);

            } catch (IOException e) {
                throw new RuntimeException("File Upload Failed");
            }
            VenueImages venueImages = VenueImages.builder()
                    .imageUrl(imageName)
                    .venue(venue)
                    .build();

            venueImagesList.add(venueImages);

        }

        venueImagesRepository.saveAll(venueImagesList);

    }
    public List<VenueImages>  getImagesByVenue(Long Venueid) {

         venueRepository.findById(Venueid)
                 .orElseThrow(()->new RuntimeException("Venue not found with id"+Venueid));
         return venueImagesRepository.findByVenueId(Venueid);
    }

    public List<VenueImages> getAllImages() {
        return venueImagesRepository.findAll();
    }


    public void deleteVenueImage(Long id) {
        VenueImages venueImage = venueImagesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));

        File file = new File(FILE_UPLOAD_DIR, venueImage.getImageUrl());

        if (file.exists()) {
            boolean deleted=file.delete();

            System.out.println("File deleted:" + deleted);
        }

        venueImagesRepository.delete(venueImage);
    }
}
