package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.entity.Reservation;
import mg.itu.ticketing.entity.SeatReservation;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.request.ReservationRequest;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final SeatPricingService seatPricingService;

    public void insert(
        final ReservationRequest request,
        final EntityManager entityManager,
        final User user
    ) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);

        List<SeatReservation> seatReservations = reservation.getSeatReservations();
        request.getSeatReservations().forEach(seatReservationRequest -> {
            SeatReservation seatReservation = new SeatReservation();
            seatReservation.setSeatsCount(seatReservationRequest.getSeatsCount());
            seatReservation.setSeatPricing(seatPricingService.getById(seatReservationRequest.getSeatPricingId(), entityManager));
            seatReservation.setReservation(reservation);

            seatReservations.add(seatReservation);
        });

        entityManager.persist(reservation);
    }
}
