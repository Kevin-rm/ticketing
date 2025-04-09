package mg.itu.ticketing.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightSearchRequest {
    private Integer departureCityId;
    private Integer arrivalCityId;
    private LocalDateTime minDepartureTimestamp;
    private LocalDateTime maxDepartureTimestamp;
}
