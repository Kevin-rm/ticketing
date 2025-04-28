package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.*;
import mg.itu.ticketing.enums.ReservationStatus;
import mg.itu.ticketing.exception.AlreadyCancelledReservationException;
import mg.itu.ticketing.exception.TooLateReservationCancellationException;
import mg.itu.ticketing.exception.TooLateReservationException;
import mg.itu.ticketing.request.ReservationRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static mg.itu.ticketing.utils.ApplicationConstants.BigDecimals.ONE_HUNDRED;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final SeatPricingService seatPricingService;
    private final DiscountService discountService;
    private final SettingsService settingsService;

    public Reservation getById(final Integer id, final EntityManager entityManager) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null)
            throw new RuntimeException("Aucune réservation trouvée avec l'identifiant: " + id);

        return reservation;
    }

    public List<Reservation> getAllByUser(final User user, final EntityManager entityManager) {
        return entityManager.createQuery("""
            SELECT r 
            FROM Reservation r 
            WHERE r.user = :user AND r.status = mg.itu.ticketing.enums.ReservationStatus.PLANNED
        """, Reservation.class)
            .setParameter("user", user)
            .getResultList();
    }

    public void insert(
        final ReservationRequest request,
        final EntityManager entityManager,
        final User user
    ) {
        final SeatPricing seatPricing = seatPricingService.getById(request.getSeatPricingId(), entityManager);
        final Settings settings = settingsService.getFirstOrNew(entityManager);

        final Integer minReservationHours = settings.getMinReservationHours();
        if (Duration.between(LocalDateTime.now(), seatPricing.getFlight().getDepartureTimestamp()).toHours() < minReservationHours)
            throw new TooLateReservationException(minReservationHours);

        Reservation reservation = new Reservation();
        reservation.setAdultCount(request.getAdultCount());
        reservation.setChildCount(request.getChildCount());
        reservation.setSeatPricing(seatPricing);

        final BigDecimal unitPrice = seatPricing.getUnitPrice();
        final Discount discount    = seatPricing.getDiscount();
        final BigDecimal childDiscountFactor = settings.getChildDiscountPercentage()
            .divide(ONE_HUNDRED, RoundingMode.HALF_UP);

        final int adultCount = reservation.getAdultCount();
        final int childCount = reservation.getChildCount();

        final BigDecimal totalPrice;
        if (discount == null) totalPrice = unitPrice.multiply(
            BigDecimal.valueOf(adultCount)
                .add(BigDecimal.valueOf(childCount).multiply(childDiscountFactor)
        )); else {
            final BigDecimal discountFactor = ONE_HUNDRED.subtract(discount.getPercentage())
                .divide(ONE_HUNDRED, RoundingMode.HALF_UP);
            final int availableSeatCount = discountService.countAvailableSeats(discount, entityManager);

            final int adultsWithDiscount   = Math.min(adultCount, availableSeatCount);
            final int childrenWithDiscount = Math.min(childCount, availableSeatCount - adultsWithDiscount);

            totalPrice = unitPrice.multiply(BigDecimal.valueOf(adultsWithDiscount)).multiply(discountFactor)
                .add(BigDecimal.valueOf(adultCount - adultsWithDiscount))
                .add(BigDecimal.valueOf(childrenWithDiscount).multiply(discountFactor).multiply(childDiscountFactor))
                .add(BigDecimal.valueOf(childCount - childrenWithDiscount).multiply(childDiscountFactor));
        }

        reservation.setTotalPrice(totalPrice);
        reservation.setUser(user);

        entityManager.persist(reservation);
    }

    public void cancel(final Reservation reservation, final EntityManager entityManager) {
        if (reservation.getStatus() == ReservationStatus.CANCELLED)
            throw new AlreadyCancelledReservationException(reservation);

        final Integer minCancellationHours = settingsService.getFirstOrNew(entityManager).getMinCancellationHours();
        if (Duration.between(reservation.getTimestamp(), LocalDateTime.now()).toHours() > minCancellationHours)
            throw new TooLateReservationCancellationException(minCancellationHours);

        reservation.setStatus(ReservationStatus.CANCELLED);
        entityManager.merge(reservation);
    }
}
