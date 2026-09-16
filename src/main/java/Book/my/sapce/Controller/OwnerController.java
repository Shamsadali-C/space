package Book.my.sapce.Controller;

import Book.my.sapce.DTO.TimeSlotRequestDTO;
import Book.my.sapce.DTO.VenueRequestDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {


    private final VenueService venueService;
    private final VenueImagesService venueImagesService;
    private final BookingService bookingService;
    private final TimeSlotService timeSlotService;
    private final SlotDurationService slotDurationService;
    public OwnerController(VenueService venueService,
                           VenueImagesService venueImagesService,
                           BookingService bookingService,
                           TimeSlotService timeSlotService,
                           SlotDurationService slotDurationService){

        this.venueService=venueService;
        this.venueImagesService=venueImagesService;
        this.bookingService=bookingService;
        this.timeSlotService=timeSlotService;
        this.slotDurationService=slotDurationService;
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/venues")
    public ResponseEntity<?> getOwnerVenues(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                venueService.getOwnerVenues(username)
        );
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/bookings")
    public ResponseEntity<?> getOwnerBookings(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                bookingService.getOwnerBookings(username)
        );
    }


    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add-venue")
    public Venue addVenue(@Valid @RequestBody VenueRequestDTO venueRequest) {
        return venueService.addVenue(venueRequest);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/maintenance/{venueId}")
    public ResponseEntity<?> maintenance(@PathVariable Long venueId,@RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(venueService.maintenance(venueId, date));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/available/{venueId}")
    public ResponseEntity<?>available (@PathVariable Long venueId){
        return ResponseEntity.ok(venueService.available(venueId));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/holiday/{venueId}")
    public ResponseEntity<?> holiday(@PathVariable Long venueId,@RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(venueService.holiday(venueId, date));
    }

    @PostMapping(value = "/venue/{venueId}/images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@PathVariable Long venueId,@RequestParam("files") List<MultipartFile> files) {

        venueImagesService.uploadfile(venueId, files);
        return ResponseEntity .status(HttpStatus.CREATED)
                .body("Images uploaded successfully");
    }

    @GetMapping("/venue/{venueId}/images")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> getImages( @PathVariable Long venueId) {

        return ResponseEntity.ok(
                venueImagesService.getImagesByVenue(venueId)
        );
    }

    @PutMapping( value = "/venue/images/{imageId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> updateImage(
            @PathVariable Long imageId,
            @RequestParam("file") MultipartFile file) {

        venueImagesService.updateVenueImage(
                imageId,
                file
        );

        return ResponseEntity.ok("Image updated successfully");
    }

    @DeleteMapping("/venue/images/{imageId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long imageId) {

        venueImagesService.deleteVenueImage(imageId);

        return ResponseEntity.ok(
                "Image deleted successfully"
        );
    }

//    @PreAuthorize("hasRole('OWNER')")
//    @PutMapping("/Approve/{bookingId}")
//    public ResponseEntity<?> approve(@PathVariable Long bookingId){
//        return ResponseEntity.ok(bookingService.approve(bookingId));
//    }
//
//    @PreAuthorize("hasRole('OWNER')")
//      @PutMapping("/reject/{bookingId}")
//      public ResponseEntity<?> reject(@PathVariable Long bookingId) {
//
//       return ResponseEntity.ok(
//            bookingService.reject(bookingId));
//    }



    @DeleteMapping("/venue/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "venue deleted successfully";
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/venue/{venueId}/slots")
    public ResponseEntity<?> createSlot(
            @PathVariable Long venueId,
            @RequestBody TimeSlotRequestDTO request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                timeSlotService.createSlot(
                        venueId,
                        request,
                        username
                )
        );
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/durations")
    public ResponseEntity<?> getActiveDurations() {

        return ResponseEntity.ok(
                slotDurationService.getActiveDurations()
        );
    }



}


