package Book.my.sapce.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentOrderResponseDTO {

    public Long bookingId;
    public String orderId;
    public String keyId;
    public Long amount;
    public String currency;

}
