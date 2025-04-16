package mg.itu.ticketing.dto;

import java.math.BigDecimal;

public record SeatWithPricingDTO(
    Integer    seatId,
    Integer    seatCount,
    BigDecimal unitPrice,
    String     seatTypeDesignation,
    Integer    flightId
) { }
