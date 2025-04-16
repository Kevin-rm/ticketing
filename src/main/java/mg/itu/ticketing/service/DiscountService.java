package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Discount;
import mg.itu.ticketing.request.DiscountRequest;
import mg.matsd.javaframework.core.annotations.Component;

@RequiredArgsConstructor
@Component
public class DiscountService {
    private final SeatPricingService seatPricingService;

    public void insert(final DiscountRequest discountRequest, final EntityManager entityManager) {
        Discount discount = new Discount();
        discount.setPercentage(discountRequest.getPercentage());
        discount.setSeatCount(discountRequest.getSeatsCount());
        discount.setSeatPricing(seatPricingService.getById(discountRequest.getSeatPricingId(), entityManager));

        entityManager.persist(discount);
    }
}
