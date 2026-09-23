package Book.my.sapce.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(name = "booking")


public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="venue_id",nullable = false)
    private Venue venue;


    private LocalDate bookingDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Double totalPrice;
    private Double hourlyPrice;
    private Integer durationHours;
    private Double advanceAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;
    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;
    @Column(name = "razorpay_signature")
    private String razorpaySignature;


    private Double refundAmount;

    private LocalDateTime cancelledAt;

    private LocalDateTime holdExpiresAt;


//    @ManyToOne
//    @JoinColumn(name = "time_slot_id", nullable = false)
//    private TimeSlot timeSlot;




}
