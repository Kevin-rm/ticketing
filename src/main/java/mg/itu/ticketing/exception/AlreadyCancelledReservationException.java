package mg.itu.ticketing.exception;

import lombok.Getter;
import mg.itu.ticketing.entity.Reservation;

@Getter
public class AlreadyCancelledReservationException extends RuntimeException {
    private final Reservation reservation;

    public AlreadyCancelledReservationException(final Reservation reservation) {
        super("La réservation est déjà annulée");
        this.reservation = reservation;
    }
}
