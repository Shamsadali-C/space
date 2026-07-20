package Book.my.sapce.Controller;

import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Service.VenueImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/venue/venue_images")
@RequiredArgsConstructor
public class VenueImagesController {

     @Autowired
     public VenueImagesService venueImagesService;

     public static final String FILE_UPLOAD_DIR="C:\\Users\\Shamsadali\\OneDrive\\intership\\upload\\";

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> addImage(@ModelAttribute VenueImagesDTO dto, @RequestParam ("file")List<MultipartFile> file) {
//        venueImagesService.addImage(dto, file);
//     try {
//         File dir = new File(FILE_UPLOAD_DIR);
//         if (!dir.exists())
//         dir.mkdirs();
//
//
//         File filepath = FILE_UPLOAD_DIR + file.getOriginalFilename();
//         file.transferTo(new File(filepath));
//
//     } catch (Exception e){
//         return ResponseEntity.;
//     }
//
//        return ResponseEntity.ok("Images uploaded successfully.");
//    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadfile(@RequestParam ("file")MultipartFile file) {
          try {
                 File dir = new File(FILE_UPLOAD_DIR);
                 if (!dir.exists())
                      dir.mkdirs();

                 File filepath = new File(FILE_UPLOAD_DIR + file.getOriginalFilename());
                 file.transferTo(filepath);

                 return ResponseEntity
                         .status(HttpStatus.CREATED)
                         .body("file upload successfully" + file.getOriginalFilename());
          }catch (IOException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file" + e.getMessage());
          }

    }

    @GetMapping("{venueId}")
    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueImagesService.getImagesByVenue(venueId));
    }


    @DeleteMapping
    public String deleteImage(@PathVariable Long venueId){
        venueImagesService.deleteVenueId(venueId);
                return("Image Deleted Successfully");
    }

    @GetMapping
    public List<VenueImages> getImages() {
        return venueImagesService.getAllImages();
    }


}
