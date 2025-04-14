package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Reservation;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.request.ReservationRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final SeatPricingService seatPricingService;

    public List<Reservation> getAllByUser(final User user, final EntityManager entityManager) {
        return entityManager.createQuery("SELECT r FROM Reservation r WHERE r.user = :user", Reservation.class)
            .setParameter("user", user)
            .getResultList();
    }

    public void insert(
        final ReservationRequest request,
        final EntityManager entityManager,
        final User user
    ) {
        Reservation reservation = new Reservation();
        reservation.setAdultCount(request.getAdultCount());
        reservation.setChildCount(request.getChildCount());
        reservation.setSeatPricing(seatPricingService.getById(request.getSeatPricingId(), entityManager));
        reservation.setUser(user);

        entityManager.persist(reservation);
    }
}
