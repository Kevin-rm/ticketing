package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Discount;
import mg.itu.ticketing.request.DiscountRequest;
import mg.matsd.javaframework.core.annotations.Nullable;
import mg.matsd.javaframework.di.annotations.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DiscountService {
    private final SeatPricingService seatPricingService;

    public List<Discount> getAll(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT d FROM Discount d ORDER BY d.id DESC", Discount.class)
            .getResultList();
    }

    public Discount getById(final Integer id, final EntityManager entityManager) {
        Discount discount = entityManager.find(Discount.class, id);
        if (discount == null)
            throw new RuntimeException("Aucune promotion trouvée avec l'identifiant: " + id);

        return discount;
    }

    public void insert(final DiscountRequest request, final EntityManager entityManager) {
        entityManager.persist(populateFromRequest(new Discount(), request, entityManager));
    }
    
    public void update(final Discount discount, final DiscountRequest request, final EntityManager entityManager) {
        entityManager.merge(populateFromRequest(discount, request, entityManager));
    }
    
    public void delete(final Discount discount, final EntityManager entityManager) {
        entityManager.remove(discount);
    }

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

    private Discount populateFromRequest(
        final Discount discount, final DiscountRequest request, final EntityManager entityManager
    ) {
        discount.setPercentage(request.getPercentage());
        discount.setSeatCount(request.getSeatCount());
        discount.setSeatPricing(seatPricingService.getById(request.getSeatPricingId(), entityManager));

        return discount;
    }
}
