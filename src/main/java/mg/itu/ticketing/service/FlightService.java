package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.request.FlightRequest;
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
