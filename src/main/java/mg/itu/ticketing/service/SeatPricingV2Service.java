package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.entity.Seat;
import mg.itu.ticketing.entity.SeatPricing;
import mg.itu.ticketing.entity.SeatPricingV2;
import mg.itu.ticketing.request.SeatPricingRequestV2;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.di.annotations.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SeatPricingV2Service {

    public List<SeatPricing> getAll(final EntityManager entityManager) {
        return entityManager.createQuery("""
            SELECT sp FROM SeatPricing sp
            JOIN FETCH sp.flight f
            JOIN FETCH sp.seat s
            JOIN FETCH s.seatType
            JOIN FETCH f.departureCity
            JOIN FETCH f.arrivalCity
        """, SeatPricing.class).getResultList();
    }

    public SeatPricing getById(final Integer id, final EntityManager entityManager) {
        SeatPricing seatPricing = entityManager.find(SeatPricing.class, id);
        if (seatPricing == null)
            throw new RuntimeException("Aucun tarif de siège trouvé avec l'identifiant: " + id);

        return seatPricing;
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

    public void insert(final SeatPricingRequestV2 request, final EntityManager entityManager) {

    }

    // For the list page
    public List<SeatPricingV2> getByFlight(final Flight flight, final EntityManager entityManager) {
        return entityManager.createQuery("""
            SELECT spv2
            FROM SeatPricingV2 spv2
            WHERE spv2.flight = :flight
        """)
            .setParameter("flight", flight)
            .getResultList();
    }

    public List<SeatPricingV2> getFirstBySeatTypeForFlight(final Flight flight, final EntityManager entityManager) {
        return entityManager.createQuery("""
            SELECT sp 
            FROM SeatPricingV2 sp
            WHERE sp.flight = :flight
            AND sp.id = (
                SELECT MIN(sp2.id)
                FROM SeatPricingV2 sp2
                WHERE sp2.flight = :flight
                AND sp2.seat.seatType = sp.seat.seatType
            )
            ORDER BY sp.id
        """, SeatPricingV2.class)
            .setParameter("flight", flight)
            .getResultList();
    }
    

}
