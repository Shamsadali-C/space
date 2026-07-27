package Book.my.sapce.Controller;

import Book.my.sapce.DTO.VenueImagesDTO;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Service.VenueImagesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Destination;
import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/venue/venue_images")
@RequiredArgsConstructor
public class VenueImagesController {

     @Autowired
     public VenueImagesService venueImagesService;



    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadfile(@Valid @ModelAttribute VenueImagesDTO dto, @RequestParam("files") List<MultipartFile> file) {

        venueImagesService.uploadfile(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Image Uploaded Succesfully");


//          try {
//                 File dir = new File(FILE_UPLOAD_DIR);
//                 if (!dir.exists()){
//                      dir.mkdirs();
//                      }
//                    if (file.isEmpty()){
//                        return ResponseEntity
//                                .badRequest()
//                                .body("Select A File");
//                    }
//
//                 File filepath = new File(FILE_UPLOAD_DIR + file.getOriginalFilename());
//                 file.transferTo(filepath);
//
//                 return ResponseEntity
//                         .status(HttpStatus.CREATED)
//                         .body("file upload successfully" + file.getOriginalFilename());
//          }catch (IOException e){
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error uploading file" + e.getMessage());
//          }

    }

    @GetMapping("/{venueId}")
    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long VenueId) {
        return ResponseEntity.ok(venueImagesService.getImagesByVenue(VenueId));
    }


    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id){
        venueImagesService.deleteVenueId(id);
                return("Image Deleted Successfully");
    }

    @GetMapping
    public List<VenueImages> getImages() {
        return venueImagesService.getAllImages();
    }

}
