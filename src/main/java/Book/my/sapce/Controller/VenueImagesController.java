package Book.my.sapce.Controller;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Service.VenueImagesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/venue/venue_images")
@RequiredArgsConstructor
public class VenueImagesController {


     public  final  VenueImagesService venueImagesService;



    @PostMapping(value = "/{venueId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadFile(
            @PathVariable Long venueId,
            @RequestParam("files") List<MultipartFile> files) {

        venueImagesService.uploadfile(venueId, files);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Images uploaded successfully");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long id) {
//        return ResponseEntity.ok(venueImagesService.getImagesByVenue(id));
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {

        venueImagesService.deleteVenueImage(id);

        return ResponseEntity.ok("Image deleted successfully");
    }

//    @GetMapping
//    public List<VenueImages> getImages() {
//        return venueImagesService.getAllImages();
//    }

}
