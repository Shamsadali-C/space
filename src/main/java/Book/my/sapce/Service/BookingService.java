package Book.my.sapce.Service;

import Book.my.sapce.DTO.BookingDetailsDTO;
import Book.my.sapce.DTO.PaymentOrderResponseDTO;
import Book.my.sapce.DTO.PaymentVerifyDTO;
import Book.my.sapce.Model.*;
import Book.my.sapce.Repository.BookingRepository;
import Book.my.sapce.Repository.TimeSlotRepository;
import Book.my.sapce.Repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          TimeSlotRepository timeSlotRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Transactional
    public PaymentOrderResponseDTO createPaymentOrder(Long userId, List<Long> slotIds) throws Exception {

        if (slotIds == null || slotIds.isEmpty()) {
            throw new RuntimeException("Please select at least one time slot");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TimeSlot> selectedSlots = timeSlotRepository.findAllById(slotIds);

        if (selectedSlots.size() != slotIds.size()) {
            throw new RuntimeException("One or more time slots not found");
        }

        for (TimeSlot slot : selectedSlots) {
            if (slot.getStatus() != TimeSlotStatus.AVAILABLE) {
                throw new RuntimeException(
                        "Time slot " + slot.getStartTime() + " - " + slot.getEndTime() + " is no longer available"
                );
            }
        }

        selectedSlots.sort(Comparator.comparing(TimeSlot::getStartTime));

        for (int i = 0; i < selectedSlots.size() - 1; i++) {
            TimeSlot current = selectedSlots.get(i);
            TimeSlot next = selectedSlots.get(i + 1);

            if (!current.getEndTime().equals(next.getStartTime())) {
                throw new RuntimeException("Selected time slots must be consecutive");
            }
        }

        TimeSlot firstSlot = selectedSlots.get(0);
        TimeSlot lastSlot = selectedSlots.get(selectedSlots.size() - 1);
        Venue venue = firstSlot.getVenue();

        int durationHours = selectedSlots.size();
        double hourlyPrice = venue.getPrice();
        double totalPrice = hourlyPrice * durationHours;
        double advanceAmount = totalPrice * 0.25;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setVenue(venue);
        booking.setBookingDate(firstSlot.getSlotDate());
        booking.setStartTime(firstSlot.getStartTime());
        booking.setEndTime(lastSlot.getEndTime());
        booking.setDurationHours(durationHours);
        booking.setHourlyPrice(hourlyPrice);
        booking.setTotalPrice(totalPrice);
        booking.setAdvanceAmount(advanceAmount);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        for (TimeSlot slot : selectedSlots) {
            slot.setStatus(TimeSlotStatus.PENDING);
        }
        timeSlotRepository.saveAll(selectedSlots);

        booking = bookingRepository.save(booking);

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        long amountInPaise = Math.round(advanceAmount * 100);

        JSONObject notes = new JSONObject();
        notes.put("booking_id", booking.getId());
        notes.put("user_id", userId);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "BOOKING_" + booking.getId());
        orderRequest.put("notes", notes);

        Order order = razorpay.orders.create(orderRequest);
        String razorpayOrderId = order.get("id");

        booking.setRazorpayOrderId(razorpayOrderId);
        bookingRepository.save(booking);

        return new PaymentOrderResponseDTO(
                booking.getId(),
                razorpayOrderId,
                razorpayKeyId,
                amountInPaise,
                "INR"
        );
    }

    @Transactional
    public Booking verifyPayment(Long userId, PaymentVerifyDTO request) throws Exception {

        Booking booking = bookingRepository.findByIdAndUserId(request.getBookingId(), userId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getRazorpayOrderId().equals(request.getRazorpayOrderId())) {
            throw new RuntimeException("Invalid Razorpay order");
        }

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", booking.getRazorpayOrderId());
        options.put("razorpay_payment_id", request.getRazorpayPaymentId());
        options.put("razorpay_signature", request.getRazorpaySignature());

        boolean verified = Utils.verifyPaymentSignature(options, razorpayKeySecret);

          if (!verified) {
            throw new RuntimeException("Payment verification failed");
                 }

        booking.setRazorpayPaymentId(request.getRazorpayPaymentId());
        booking.setRazorpaySignature(request.getRazorpaySignature());
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking.setBookingStatus(BookingStatus.BOOKED);

        List<TimeSlot> slots = timeSlotRepository.findByVenueIdAndSlotDate(
                booking.getVenue().getId(),
                booking.getBookingDate()
        );

        for (TimeSlot slot : slots) {
            boolean insideBooking = !slot.getStartTime().isBefore(booking.getStartTime())
                    && !slot.getEndTime().isAfter(booking.getEndTime());

            if (insideBooking && slot.getStatus() == TimeSlotStatus.PENDING) {
                slot.setStatus(TimeSlotStatus.BOOKED);
            }
        }
        timeSlotRepository.saveAll(slots);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking paymentFailure(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                              .orElseThrow(() ->new RuntimeException("Booking Not Found"));

        if (booking.getPaymentStatus() == PaymentStatus.PAID) {
            return booking;
        }

        booking.setPaymentStatus(PaymentStatus.FAILED);
        booking.setBookingStatus(BookingStatus.CANCELLED);

        List<TimeSlot> slots =
                timeSlotRepository.findByVenueIdAndSlotDate( booking.getVenue().getId(),
                                                              booking.getBookingDate());

        for (TimeSlot slot : slots) {
            boolean insideBooking =!slot.getStartTime().isBefore(booking.getStartTime())
                            &&!slot.getEndTime().isAfter(booking.getEndTime());

            if (insideBooking && slot.getStatus() == TimeSlotStatus.PENDING) {
                slot.setStatus(TimeSlotStatus.AVAILABLE);
            }
        }

        timeSlotRepository.saveAll(slots);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long userId, Long bookingId) throws Exception {

        Booking booking = bookingRepository
                .findByIdAndUserId(bookingId, userId)
                .orElseThrow(() ->new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.BOOKED) {
            throw new RuntimeException("Only booked reservations can be cancelled");
        }

        if (booking.getPaymentStatus() != PaymentStatus.PAID) {
            throw new RuntimeException("Payment was not completed for this booking");
        }

        if (booking.getRazorpayPaymentId() == null) {
            throw new RuntimeException("Razorpay payment ID not found");
        }

        LocalDateTime bookingStart =LocalDateTime.of(booking.getBookingDate(), booking.getStartTime());

        LocalDateTime cancellationTime =LocalDateTime.now();

        Duration remainingTime =Duration.between(
                        cancellationTime,
                        bookingStart
                );

        if (remainingTime.isNegative()|| remainingTime.isZero()) {

            throw new RuntimeException(
                    "This booking cannot be cancelled because the booking has already started"
            );
        }

        double refundPercentage;

        if (remainingTime.compareTo(
                Duration.ofHours(48)) > 0) {

            refundPercentage = 100.0;

        } else if (remainingTime.compareTo(
                Duration.ofHours(24)) >= 0) {

            refundPercentage = 75.0;

        } else if (remainingTime.compareTo(
                Duration.ofHours(6)) >= 0) {

            refundPercentage = 50.0;

        } else {

            refundPercentage = 0.0;
        }


        double advanceAmount =
                booking.getAdvanceAmount();

        double refundAmount =
                advanceAmount *
                        refundPercentage /
                        100.0;

        if (refundAmount > 0) {

            RazorpayClient razorpay =
                    new RazorpayClient(
                            razorpayKeyId,
                            razorpayKeySecret
                    );

            long refundAmountInPaise =
                    Math.round(refundAmount * 100);

            JSONObject refundRequest =
                    new JSONObject();

            refundRequest.put(
                    "amount",
                    refundAmountInPaise
            );

            refundRequest.put(
                    "speed",
                    "normal"
            );

            Refund refund =
                    razorpay.payments.refund(
                            booking.getRazorpayPaymentId(),
                            refundRequest
                    );

            System.out.println(
                    "Razorpay refund created: "
                            + refund.get("id")
            );

            booking.setPaymentStatus(
                    PaymentStatus.REFUNDED
            );

        } else {

            booking.setPaymentStatus(
                    PaymentStatus.PAID
            );
        }

        booking.setRefundAmount(
                refundAmount
        );

        booking.setCancelledAt(
                cancellationTime
        );

        booking.setBookingStatus(BookingStatus.CANCELLED
        );

        List<TimeSlot> slots =
                timeSlotRepository
                        .findByVenueIdAndSlotDate(
                                booking.getVenue().getId(),
                                booking.getBookingDate()
                        );

        for (TimeSlot slot : slots) {

            boolean insideBooking =!slot.getStartTime().isBefore(booking.getStartTime())&&
                            !slot.getEndTime().isAfter(booking.getEndTime());

            if (insideBooking && slot.getStatus()== TimeSlotStatus.BOOKED) {

                slot.setStatus(TimeSlotStatus.AVAILABLE);
            }
        }

        timeSlotRepository.saveAll(slots);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public BookingDetailsDTO getBookingDetails(Long bookingId, User user) {

        Booking booking = bookingRepository
                .findByIdAndUserId(bookingId, user.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingDetailsDTO dto = new BookingDetailsDTO();

        dto.setBookingId(booking.getId());
        dto.setBookingStatus(booking.getBookingStatus());

        return dto;
    }

    public List<Booking> getOwnerBookings(String username) {
        return bookingRepository.findByVenueOwnerUsername(username);
    }

    public List<Booking> getUserBookings(Long userId) {

        return bookingRepository.findByUserId(userId);
    }
    @Transactional
    public void cancelBookingWithFullRefund(Booking booking) throws Exception {

        if (booking.getBookingStatus()
                != BookingStatus.BOOKED) {
            return;
        }

        if (booking.getPaymentStatus()
                != PaymentStatus.PAID) {
            return;
        }

        double refundAmount =
                booking.getAdvanceAmount();

        if (refundAmount > 0) {

            if (booking.getRazorpayPaymentId() == null) {

                throw new RuntimeException(
                        "Razorpay payment ID not found for booking "
                                + booking.getId()
                );
            }

            RazorpayClient razorpay =
                    new RazorpayClient(
                            razorpayKeyId,
                            razorpayKeySecret
                    );

            long refundAmountInPaise =
                    Math.round(refundAmount * 100);

            JSONObject refundRequest =
                    new JSONObject();

            refundRequest.put(
                    "amount",
                    refundAmountInPaise
            );

            refundRequest.put(
                    "speed",
                    "normal"
            );

            Refund refund =
                    razorpay.payments.refund(
                            booking.getRazorpayPaymentId(),
                            refundRequest
                    );

            System.out.println(
                    "Razorpay refund created: "
                            + refund.get("id")
            );

            booking.setPaymentStatus(
                    PaymentStatus.REFUNDED
            );

        } else {

            booking.setPaymentStatus(
                    PaymentStatus.PAID
            );
        }

        booking.setBookingStatus(
                BookingStatus.CANCELLED
        );

        booking.setRefundAmount(
                refundAmount
        );

        booking.setCancelledAt(
                LocalDateTime.now()
        );

        List<TimeSlot> slots =
                timeSlotRepository
                        .findByVenueIdAndSlotDate(
                                booking.getVenue().getId(),
                                booking.getBookingDate()
                        );

        for (TimeSlot slot : slots) {

            boolean insideBooking =
                    !slot.getStartTime()
                            .isBefore(
                                    booking.getStartTime()
                            )

                            &&

                            !slot.getEndTime()
                                    .isAfter(
                                            booking.getEndTime()
                                    );

            if (insideBooking
                    && slot.getStatus()
                    == TimeSlotStatus.BOOKED) {

                slot.setStatus(
                        TimeSlotStatus.AVAILABLE
                );
            }
        }

        timeSlotRepository.saveAll(slots);

        bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBookingsForVenueDate(Long venueId,LocalDate date) throws Exception {

        List<Booking> bookings =bookingRepository.findByVenueIdAndBookingDate(venueId,date );

        for (Booking booking : bookings) {
            if (booking.getBookingStatus()== BookingStatus.BOOKED) {

                cancelBookingWithFullRefund(booking);
            }
        }
    }
}