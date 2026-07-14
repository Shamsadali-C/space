package Book.my.sapce.Controller;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Service.VenueImagesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/venue/venue_images")
@RequiredArgsConstructor
public class VenueImagesController {

     @Autowired
     public VenueImagesService venueImagesService;



    @PostMapping
    public ResponseEntity<VenueImages> addImage(
            @Valid @RequestBody VenueImagesDTO dto) {
        return ResponseEntity.ok(venueImagesService.addImage(dto));

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long venueId) {
//
//        return ResponseEntity.ok(venueImagesService.getImagesByVenue(venueId));
//    }

    @GetMapping("{venueId}")
    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueImagesService.getImagesByVenue(venueId));
    }


    @GetMapping
    public List<VenueImages> getImages() {
        return venueImagesService.getAllImages();
    }


}
