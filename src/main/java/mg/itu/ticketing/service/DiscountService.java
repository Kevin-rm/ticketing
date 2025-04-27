package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Discount;
import mg.itu.ticketing.request.DiscountRequest;
import mg.matsd.javaframework.core.annotations.Component;
import mg.matsd.javaframework.core.annotations.Nullable;

@RequiredArgsConstructor
@Component
public class DiscountService {
    private final SeatPricingService seatPricingService;

    public int countAvailableSeats(@Nullable final Discount discount, final EntityManager entityManager) {
        if (discount == null) return 0;

        return Math.max(0, discount.getSeatCount() - entityManager.createQuery("""
            SELECT COALESCE(SUM(r.adultCount + r.childCount), 0)
            FROM Reservation r
            WHERE r.seatPricing = :seatPricing AND r.status = mg.itu.ticketing.enums.ReservationStatus.PLANNED
        """, Integer.class)
            .setParameter("seatPricing", discount.getSeatPricing())
            .getSingleResult());
    }

    public void insert(final DiscountRequest discountRequest, final EntityManager entityManager) {
        Discount discount = new Discount();
        discount.setPercentage(discountRequest.getPercentage());
        discount.setSeatCount(discountRequest.getSeatsCount());
        discount.setSeatPricing(seatPricingService.getById(discountRequest.getSeatPricingId(), entityManager));

        entityManager.persist(discount);
    }
}
