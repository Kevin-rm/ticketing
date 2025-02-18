package mg.itu.ticketing.request;

import lombok.Data;
import mg.matsd.javaframework.validation.constraints.basic.Required;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationRequest {
    private List<SeatReservationRequest> seatReservations = new ArrayList<>();

    @Data
    public static class SeatReservationRequest {
        @Required
        private Integer seatsCount;

        @Required
        private Integer seatPricingId;
    }
}
