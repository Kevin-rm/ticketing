package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.dto.SeatWithPricingDTO;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.entity.Plane;
import mg.itu.ticketing.entity.Seat;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@Component
public class SeatService {

    public List<Seat> getAllByPlane(final Plane plane, final EntityManager entityManager) {
        return entityManager.createQuery("SELECT s FROM Seat s WHERE s.plane = :plane", Seat.class)
            .setParameter("plane", plane)
            .getResultList();
    }

    public List<SeatWithPricingDTO> getAllWithPricingByFlight(
        final Integer flightId, final Flight flight, final EntityManager entityManager
    ) {
        return entityManager.createQuery("""
           SELECT NEW mg.itu.ticketing.dto.SeatWithPricingDTO(
                s.id,
                s.seatsCount, 
                CAST(COALESCE(sp.unitPrice, 0.00) AS BIGDECIMAL),
                s.seatType.designation, 
                :flightId)
            FROM Seat s
                LEFT JOIN SeatPricing sp ON sp.seat = s AND sp.flight = :flight
            WHERE s.plane = :plane
       """, SeatWithPricingDTO.class)
            .setParameter("flightId", flightId)
            .setParameter("flight", flight)
            .setParameter("plane", flight.getPlane())
            .getResultList();
    }

    public Seat getById(final Integer id, final EntityManager entityManager) {
        Seat seat = entityManager.find(Seat.class, id);
        if (seat == null)
            throw new RuntimeException("Aucun siège trouvé avec l'identifiant: " + id);

        return seat;
    }
}
