package mg.itu.ticketing.exception;

import lombok.Getter;

@Getter
public class TooLateReservationException extends RuntimeException {
    private final Integer minReservationHours;

    public TooLateReservationException(Integer minReservationHours) {
        super(String.format("Les réservations doivent être effectuées au moins " +
            "%d heure%c avant le départ du vol", minReservationHours, minReservationHours > 1 ? 's' : '\0'));
        this.minReservationHours = minReservationHours;
    }
}
