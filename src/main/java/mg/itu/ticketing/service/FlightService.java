package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.request.FlightRequest;
import mg.itu.ticketing.request.FlightSearchRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FlightService {
    private final CityService cityService;
    private final PlaneService planeService;

    public List<Flight> getAll(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT f FROM Flight f", Flight.class)
            .getResultList();
    }

    public Flight getById(final Integer id, final EntityManager entityManager) {
        Flight flight = entityManager.find(Flight.class, id);
        if (flight == null)
            throw new RuntimeException("Aucun vol trouvé avec l'identifiant: " + id);

        return flight;
    }

    public void insert(final FlightRequest request, final EntityManager entityManager) {
        entityManager.persist(populateFromRequest(new Flight(), request, entityManager));
    }

    public void update(
        final Flight flight, final FlightRequest request, final EntityManager entityManager
    ) {
        entityManager.merge(populateFromRequest(flight, request, entityManager));
    }

    public void delete(final Flight flight, final EntityManager entityManager) {
        entityManager.remove(flight);
    }

    public List<Flight> search(final FlightSearchRequest request, final EntityManager entityManager) {
        String sql = """
            SELECT f
            FROM Flight f 
            WHERE (:departureCityId IS NULL OR f.departureCity.id = :departureCityId) AND 
                  (:arrivalCityId   IS NULL OR f.arrivalCity.id = :arrivalCityId)
        """;

        if (request.getDepartureTimestampMin() != null)
            sql += " AND f.departureTimestamp >= :departureTimestampMin";
        if (request.getDepartureTimestampMax() != null)
            sql += " AND f.departureTimestamp <= :departureTimestampMax";

        TypedQuery<Flight> typedQuery = entityManager.createQuery(sql, Flight.class)
            .setParameter("departureCityId", request.getDepartureCityId())
            .setParameter("arrivalCityId", request.getArrivalCityId());

        if (request.getDepartureTimestampMin() != null)
            typedQuery.setParameter("departureTimestampMin", request.getDepartureTimestampMin());
        if (request.getDepartureTimestampMax() != null)
            typedQuery.setParameter("departureTimestampMax", request.getDepartureTimestampMax());

        return typedQuery.getResultList();
    }

    private Flight populateFromRequest(
        final Flight flight, final FlightRequest request, final EntityManager entityManager
    ) {
        flight.setDepartureTimestamp(request.getDepartureTimestamp());
        flight.setArrivalTimestamp(request.getArrivalTimestamp());
        flight.setDepartureCity(cityService.getById(request.getDepartureCityId(), entityManager));
        flight.setArrivalCity(cityService.getById(request.getArrivalCityId(), entityManager));
        flight.setPlane(planeService.getById(request.getPlaneId(), entityManager));

        return flight;
    }
}
