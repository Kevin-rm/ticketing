package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Reservation;
import mg.itu.ticketing.entity.SeatPricing;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.enums.ReservationStatus;
import mg.itu.ticketing.exception.AlreadyCancelledReservationException;
import mg.itu.ticketing.request.ReservationRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final SeatPricingService seatPricingService;

    public Reservation getById(final Integer id, final EntityManager entityManager) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation == null)
            throw new RuntimeException("Aucune réservation trouvée avec l'identifiant: " + id);

        return reservation;
    }

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

        SeatPricing seatPricing = seatPricingService.getById(request.getSeatPricingId(), entityManager);
        reservation.setSeatPricing(seatPricing);

        

        reservation.setUser(user);

        entityManager.persist(reservation);
    }

    public void cancel(final Reservation reservation, final EntityManager entityManager) {
        if (reservation.getStatus() == ReservationStatus.CANCELLED)
            throw new AlreadyCancelledReservationException(reservation);

        reservation.setStatus(ReservationStatus.CANCELLED);
        entityManager.merge(reservation);
    }
}
