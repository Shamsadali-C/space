package Book.my.sapce.Controller;

import Book.my.sapce.DTO.TimeSlotRequestDTO;
import Book.my.sapce.DTO.VenueRequestDTO;
import Book.my.sapce.Model.Venue;
import Book.my.sapce.Model.VenueImages;
import Book.my.sapce.Service.BookingService;
import Book.my.sapce.Service.TimeSlotService;
import Book.my.sapce.Service.VenueImagesService;
import Book.my.sapce.Service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {


    private final VenueService venueService;
    private final VenueImagesService venueImagesService;
    private final BookingService bookingService;
    private final TimeSlotService timeSlotService;
    public OwnerController(VenueService venueService,
                           VenueImagesService venueImagesService,
                           BookingService bookingService,
                           TimeSlotService timeSlotService){

        this.venueService=venueService;
        this.venueImagesService=venueImagesService;
        this.bookingService=bookingService;
        this.timeSlotService=timeSlotService;
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

//    @GetMapping("/bookings/{bookingId}")
//    public ResponseEntity<BookingDetailsDTO> getBookingDetails(
//            @PathVariable Long bookingId, @RequestParam Long userId) {
//
//        User user = new User();
//        user.setId(userId);
//
//        BookingDetailsDTO booking = bookingService.getBookingDetails(bookingId, user);
//
//        return ResponseEntity.ok(booking);
//    }





    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add-venue")
    public Venue addVenue(@Valid @RequestBody VenueRequestDTO venueRequest) {
        return venueService.addVenue(venueRequest);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/maintanence/{venueId}")
    public ResponseEntity<?> maintanence(@PathVariable Long venueId){
        return ResponseEntity.ok(venueService.maintanence(venueId));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/available/{venueId}")
    public ResponseEntity<?>available (@PathVariable Long venueId){
        return ResponseEntity.ok(venueService.available(venueId));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/venue/holiday/{venueId}")
    public ResponseEntity<?> holiday(@PathVariable Long venueId){
        return ResponseEntity.ok(venueService.holiday(venueId));
    }

    @PostMapping(value = "/venue/{venueId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @PathVariable Long venueId,
            @RequestParam("files") List<MultipartFile> files) {

        venueImagesService.uploadfile(venueId, files);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Images uploaded successfully");
    }

    @GetMapping("/venue/{venueId}/images")
    public ResponseEntity<List<VenueImages>> getImagesByVenue(
            @PathVariable Long venueId) {

        return ResponseEntity.ok(
                venueImagesService.getImagesByVenue(venueId)
        );
    }


    @DeleteMapping("/venue/images/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {

        venueImagesService.deleteVenueImage(id);

        return ResponseEntity.ok("Image deleted successfully");
    }

//    @GetMapping
//    public List<VenueImages> getImages() {
//        return venueImagesService.getAllImages();
//    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/Approve/{bookingId}")
    public ResponseEntity<?> approve(@PathVariable Long bookingId){
        return ResponseEntity.ok(bookingService.approve(bookingId));
    }

    @PreAuthorize("hasRole('OWNER')")
      @PutMapping("/reject/{bookingId}")
      public ResponseEntity<?> reject(@PathVariable Long bookingId) {

       return ResponseEntity.ok(
            bookingService.reject(bookingId));
    }


//    @GetMapping
//    public List<Booking> getBooking() {
//        return bookingService.getAllBooking();
//    }

//    @DeleteMapping("/bookings/{id}")
//    public String deleteBooking(@PathVariable Long id) {
//
//        bookingService.deleteBooking(id);
//
//        return "Booking deleted successfully";
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



}


