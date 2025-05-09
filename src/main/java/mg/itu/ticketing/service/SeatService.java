package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.dto.SeatWithPricingDTO;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.entity.Seat;
import mg.matsd.javaframework.di.annotations.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SeatService {

    public Seat getById(final Integer id, final EntityManager entityManager) {
        Seat seat = entityManager.find(Seat.class, id);
        if (seat == null)
            throw new RuntimeException("Aucun siège trouvé avec l'identifiant: " + id);

        return seat;
    }

    public List<Seat> getByIds(final Collection<Integer> ids, final EntityManager entityManager) {
        return entityManager.createQuery("SELECT s FROM Seat s WHERE s.id IN :ids", Seat.class)
            .setParameter("ids", ids)
            .getResultList();
    }

    public List<SeatWithPricingDTO> getAllWithPricingByFlight(
        final Integer flightId, final Flight flight, final EntityManager entityManager
    ) {
        return entityManager.createQuery("""
           SELECT NEW mg.itu.ticketing.dto.SeatWithPricingDTO(
                s.id,
                s.seatCount, 
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
}
