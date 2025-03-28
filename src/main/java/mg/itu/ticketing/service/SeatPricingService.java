package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.entity.Seat;
import mg.itu.ticketing.entity.SeatPricing;
import mg.matsd.javaframework.core.annotations.Component;
import mg.matsd.javaframework.core.utils.Assert;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SeatPricingService {

    public SeatPricing getById(final Integer id, final EntityManager entityManager) {
        SeatPricing seatPricing = entityManager.find(SeatPricing.class, id);
        if (seatPricing == null)
            throw new RuntimeException("Aucun tarif de siège trouvé avec l'identifiant: " + id);

        return seatPricing;
    }

    public void save(final SeatPricing seatPricing, final EntityManager entityManager) {
        Assert.notNull(seatPricing);

        if (entityManager.contains(seatPricing))
             entityManager.merge(seatPricing);
        else entityManager.persist(seatPricing);
    }

    public List<SeatPricing> getByFlightAndSeats(
        final Flight flight, final Collection<Seat> seats, final EntityManager entityManager
    ) {
        return entityManager.createQuery("""
            SELECT sp 
            FROM SeatPricing sp 
            WHERE sp.flight = :flight AND sp.seat IN :seats
        """, SeatPricing.class)
            .setParameter("flight", flight)
            .setParameter("seats", seats)
            .getResultList();
    }
}
