package mg.itu.ticketing.dto;

import java.math.BigDecimal;

public record SeatWithPricingDTO(
    Integer    seatId,
    Integer    seatsCount,
    BigDecimal unitPrice,
    String     seatTypeDesignation,
    Integer    flightId
) { }
