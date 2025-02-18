package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.ticketing.entity.Flight;
import mg.matsd.javaframework.validation.constraints.basic.Required;

import java.time.LocalDateTime;

@Data
public final class FlightRequest {
    @Required
    private LocalDateTime departureTimestamp;

    @Required
    private LocalDateTime arrivalTimestamp;

    @Required
    private Integer departureCityId;

    @Required
    private Integer arrivalCityId;

    @Required
    private Integer planeId;

    public static FlightRequest fromFlight(final Flight flight) {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setDepartureTimestamp(flight.getDepartureTimestamp());
        flightRequest.setArrivalTimestamp(flight.getArrivalTimestamp());
        flightRequest.setDepartureCityId(flight.getDepartureCity().getId());
        flightRequest.setArrivalCityId(flight.getArrivalCity().getId());
        flightRequest.setPlaneId(flight.getPlane().getId());

        return flightRequest;
    }
}
