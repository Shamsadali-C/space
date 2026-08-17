package Book.my.sapce.Controller;

import Book.my.sapce.DTO.OwnerRequestDTO;
import Book.my.sapce.DTO.OwnerRequestResponseDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.OwnerRequestRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Service.BookingService;
import Book.my.sapce.Service.UserService;
import Book.my.sapce.Service.VenueImagesService;
import Book.my.sapce.Service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {


    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final VenueService venueService;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final VenueImagesService venueImagesService;
    private final OwnerRequestRepository ownerRequestRepository;

    public UserController(UserService userService,
                          BookingRepository bookingRepository,
                          UserRepository userRepository,
                          VenueService venueService,
                          BookingService bookingService,
                          VenueImagesService venueImagesService,
                          OwnerRequestRepository ownerRequestRepository){

        this.userService=userService;
        this.bookingRepository=bookingRepository;
        this.userRepository=userRepository;
        this.venueService=venueService;
        this.bookingService=bookingService;
        this.venueImagesService=venueImagesService;
        this.ownerRequestRepository=ownerRequestRepository;
    }




    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PutMapping("/{id}")
    public User updateuser(@PathVariable Long id,@RequestBody User user){
        user.setId(id);
        return userService.updateuser(id, user);

    }
    @GetMapping("/images/{id}")
    public ResponseEntity<List<VenueImages>> getImagesByVenue(@PathVariable Long id) {
        return ResponseEntity.ok(venueImagesService.getImagesByVenue(id));
    }

    @GetMapping("/Profile")
    public ResponseEntity<?> getprofile(Authentication authentication){
        String username= authentication.getName();
        User user=userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException());

        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/owner-request")
    public ResponseEntity<?> requestOwnerRole(
            @Valid @RequestBody OwnerRequestDTO requestDTO,
            Authentication authentication
    ) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        Optional<OwnerRequest> existingRequest =
                ownerRequestRepository.findByUserIdAndStatus(
                        user.getId(),                              // Prevent duplicate pending request
                        OwnerRequestStatus.PENDING
                );

        if (existingRequest.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("You already have a pending owner request");
        }

        OwnerRequest request = OwnerRequest.builder()
                .user(user)
                .venueName(requestDTO.getVenueName())
                .address(requestDTO.getAddress())
                .phone(requestDTO.getPhone())
                .status(OwnerRequestStatus.PENDING)
                .build();

        ownerRequestRepository.save(request);

        return ResponseEntity.ok(
                "Owner request sent successfully. Waiting for admin approval."
        );
    }

    @GetMapping("/venues")
    public List<Venue> getVenue() {
        return venueService.getAllVenue();
    }

//    @PostMapping("/{userId}/{venueId}")
//    public Booking CreateBooking(@PathVariable Long userId, @PathVariable Long venueId) {
//        try {
//            return bookingService.CreateBooking(userId, venueId);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
    @PostMapping("/booking/{venueId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createBooking(
            @PathVariable Long venueId,
            Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return ResponseEntity.ok(
                bookingService.CreateBooking(
                        user.getId(),
                        venueId
                )
        );
    }
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    @GetMapping("/owner-request/status")
    public ResponseEntity<?> getMyOwnerRequestStatus(
            Authentication authentication
    ) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Optional<OwnerRequest> request =
                ownerRequestRepository.findTopByUserIdOrderByIdDesc(
                        user.getId()
                );

        if (request.isEmpty()) {
            return ResponseEntity.ok(
                    "You have not submitted an owner request"
            );
        }

        OwnerRequest ownerRequest = request.get();

        return ResponseEntity.ok(
                new OwnerRequestResponseDTO(
                        ownerRequest.getId(),
                        user.getId(),
                        user.getUsername(),
                        ownerRequest.getVenueName(),
                        ownerRequest.getAddress(),
                        ownerRequest.getPhone(),
                        ownerRequest.getStatus()
                )
        );
    }
}
