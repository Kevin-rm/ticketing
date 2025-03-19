package mg.itu.ticketing.request;

import lombok.Data;
import mg.matsd.javaframework.validation.constraints.basic.Required;

import java.math.BigDecimal;

@Data
public class SeatPricingRequest {
    @Required
    private BigDecimal unitPrice;

    @Required
    private Integer seatId;
}
