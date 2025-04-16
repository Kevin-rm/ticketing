package mg.itu.ticketing.request;

import lombok.Data;
import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.number.PositiveOrZero;

@Data
public class ReservationRequest {
    @Required
    private Integer seatPricingId;

    @Required
    @PositiveOrZero
    private Integer adultCount = 0;

    @Required
    @PositiveOrZero
    private Integer childCount = 0;
}
