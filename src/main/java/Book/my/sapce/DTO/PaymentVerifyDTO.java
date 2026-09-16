package Book.my.sapce.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentVerifyDTO {

    private Long bookingId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
