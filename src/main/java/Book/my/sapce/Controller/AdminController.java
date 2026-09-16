package Book.my.sapce.Controller;

import Book.my.sapce.DTO.OwnerRequestResponseDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.OwnerRequestRepository;
import Book.my.sapce.Repository.UserRepository;
import Book.my.sapce.Repository.VenueRepository;
import Book.my.sapce.Service.SlotDurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final BookingRepository bookingRepository;
    private final OwnerRequestRepository ownerRequestRepository;
    private final SlotDurationService slotDurationService;

    public AdminController(UserRepository userRepository,
                           VenueRepository venueRepository,
                           BookingRepository bookingRepository,
                           OwnerRequestRepository ownerRequestRepository,
                           SlotDurationService slotDurationService){

        this.userRepository=userRepository;
        this.venueRepository=venueRepository;
        this.bookingRepository=bookingRepository;
        this.ownerRequestRepository=ownerRequestRepository;
        this.slotDurationService=slotDurationService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Long>> dashboard() {
        Map<String, Long> map = new HashMap<>();
//        map.put("totalUsers", userRepository.count());
        map.put("totalVenues", venueRepository.count());
        map.put("totalBookings", bookingRepository.count());

        map.put("users", userRepository.countByRole(Role.USER));
        map.put("owners", userRepository.countByRole(Role.OWNER));
//        map.put("admins", userRepository.countByRole(Role.ADMIN));


        map.put("booked", bookingRepository.countByBookingStatus(BookingStatus.BOOKED));
        map.put("pendingBookings", bookingRepository.countByBookingStatus(BookingStatus.PENDING));
        map.put("cancelledBookings", bookingRepository.countByBookingStatus(BookingStatus.CANCELLED));

        return ResponseEntity.ok(map);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/owner-requests/{id}/approve")
    public ResponseEntity<?> approveOwnerRequest(
            @PathVariable Long id
    ) {

        OwnerRequest request = ownerRequestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Owner request not found"));

        if (request.getStatus() != OwnerRequestStatus.PENDING) {
            return ResponseEntity.badRequest()
                    .body("Request has already been processed");
        }

        User user = request.getUser();

        user.setRole(Role.OWNER);
        userRepository.save(user);

        request.setStatus(OwnerRequestStatus.APPROVED);
        ownerRequestRepository.save(request);

        return ResponseEntity.ok(
                "Owner request approved successfully"
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/owner-requests/{id}/reject")
    public ResponseEntity<?> rejectOwnerRequest(
            @PathVariable Long id
    ) {

        OwnerRequest request = ownerRequestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Owner request not found"));

        if (request.getStatus() != OwnerRequestStatus.PENDING) {
            return ResponseEntity.badRequest()
                    .body("Request has already been processed");
        }

        request.setStatus(OwnerRequestStatus.REJECTED);
        ownerRequestRepository.save(request);

        return ResponseEntity.ok(
                "Owner request rejected successfully"
        );
    }


    @PutMapping("/users/{id}/make-owner")
    public ResponseEntity<?> makeOwner(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.OWNER);
        userRepository.save(user);

        return ResponseEntity.ok("User promoted to owner successfully");
    }

    @PutMapping("/users/{id}/make-user") public ResponseEntity<?> makeUser( @PathVariable Long id) {
        User user = userRepository .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.USER);
        userRepository.save(user);
        return ResponseEntity.ok( "Owner role changed to USER successfully" );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/owner-requests")
    public ResponseEntity<?> getOwnerRequests() {

        List<OwnerRequest> requests =
                ownerRequestRepository.findAll();

        List<OwnerRequestResponseDTO> response =
                requests.stream()
                        .map(request -> new OwnerRequestResponseDTO(
                                request.getId(),
                                request.getUser().getId(),
                                request.getUser().getUsername(),
                                request.getVenueName(),
                                request.getAddress(),
                                request.getPhone(),
                                request.getStatus()
                        ))
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/venues")
    public List<Venue> getVenue() {
        return venueRepository.findAll();
    }

    @GetMapping("/bookings")
    public List<Booking> getBooking() {
        return bookingRepository.findAll();

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }



    @DeleteMapping("/venues/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id) {
       Venue venue=venueRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Venue not found"));

       venueRepository.delete(venue);

       return ResponseEntity.ok("Venue deleted successfully");
    }



    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking Not Found"));

        bookingRepository.delete(booking);

        return ResponseEntity.ok(
                "Booking deleted successfully"
        );
    }

    @GetMapping("/durations")
    public ResponseEntity<List<SlotDuration>>getAllDurations() {

        return ResponseEntity.ok(
                slotDurationService.getAllDurations()
        );
    }

    @PostMapping("/durations/add")
    public ResponseEntity<?> addDuration(
            @RequestParam Integer durationMinutes) {

        try {

            return ResponseEntity.ok(
                    slotDurationService.addDuration(
                            durationMinutes
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/durations/{id}/toggle")
    public ResponseEntity<?> toggleDuration(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    slotDurationService.toggleDuration(id)
            );

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/duration/{id}")
    public ResponseEntity<?> deleteDuration(
            @PathVariable Long id) {

        try {

            slotDurationService.deleteDuration(id);

            return ResponseEntity.ok("Duration deleted successfully" );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}


