package mg.itu.ticketing.request;

import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.number.Positive;

import java.math.BigDecimal;

public record SeatPricingRequest(
    @Required Integer seatId,
    @Required @Positive BigDecimal unitPrice
) { }
