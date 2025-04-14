package mg.itu.ticketing.request;

import lombok.Data;

@Data
public class ReservationRequest {
    private Integer seatsCount;

    private Integer seatPricingId;
}
