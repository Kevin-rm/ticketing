package mg.itu.ticketing.request;

import lombok.Data;
import mg.matsd.javaframework.validation.constraints.basic.Required;

import java.math.BigDecimal;

@Data
public class DiscountRequest {
    @Required
    private BigDecimal percentage;

    @Required
    private Integer seatsCount;

    @Required
    private Integer seatPricingId;
}
