package mg.itu.ticketing.exception;

import lombok.Getter;

@Getter
public class TooLateReservationCancellationException extends RuntimeException {
    private final Integer minCancellationHours;

    public TooLateReservationCancellationException(Integer minCancellationHours) {
        super(String.format("Les annulations ne sont plus possibles après %d heure%c suivant la réservation",
            minCancellationHours, minCancellationHours > 1 ? 's' : '\0'));
        this.minCancellationHours = minCancellationHours;
    }
}
