package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.request.FlightRequest;
import mg.itu.ticketing.request.FlightSearchRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FlightService {
    private final CityService  cityService;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);

        List<Predicate> predicates = new ArrayList<>();

        final Integer departureCityId = request.getDepartureCityId();
        final Integer arrivalCityId   = request.getArrivalCityId();
        final LocalDateTime minDepartureTimestamp = request.getMinDepartureTimestamp();
        final LocalDateTime maxDepartureTimestamp = request.getMaxDepartureTimestamp();

        if (departureCityId != null)
            predicates.add(criteriaBuilder.equal(root.get("departureCity").get("id"), departureCityId));
        if (arrivalCityId != null)
            predicates.add(criteriaBuilder.equal(root.get("arrivalCity").get("id"), arrivalCityId));
        if (minDepartureTimestamp != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("departureTimestamp"), minDepartureTimestamp));
        if (maxDepartureTimestamp != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("departureTimestamp"), maxDepartureTimestamp));

        return entityManager.createQuery(criteriaQuery.where(
            predicates.toArray(new Predicate[0])
        )).getResultList();
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
