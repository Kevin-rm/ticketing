package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.SeatPricing;
import mg.itu.ticketing.request.SeatPricingRequest;
import mg.matsd.javaframework.core.annotations.Component;

@RequiredArgsConstructor
@Component
public class SeatPricingService {
    private final SeatService seatService;
    private final FlightService flightService;

    public SeatPricing getById(final Integer id, final EntityManager entityManager) {
        SeatPricing seatPricing = entityManager.find(SeatPricing.class, id);
        if (seatPricing == null)
            throw new RuntimeException("Aucun tarif de siège trouvé avec l'identifiant: " + id);

        return seatPricing;
    }

    public void insert(final SeatPricingRequest request, final EntityManager entityManager) {
        SeatPricing seatPricing = new SeatPricing();
        seatPricing.setUnitPrice(request.getUnitPrice());
        seatPricing.setSeat(seatService.getById(request.getSeatId(), entityManager));
        seatPricing.setFlight(flightService.getById(request.getFlightId(), entityManager));

        entityManager.persist(seatPricing);
    }
}
