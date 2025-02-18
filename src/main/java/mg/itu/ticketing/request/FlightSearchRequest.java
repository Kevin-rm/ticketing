package mg.itu.ticketing.request;

import lombok.Data;

@Data
public class FlightSearchRequest {
    private Integer departureCityId;
    private Integer arrivalCityId;
    private String  departureTimestampMin;
    private String  departureTimestampMax;
}
