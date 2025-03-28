package mg.itu.ticketing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReservationStatus {
    PLANNED("Planifiée"),
    CANCELLED("Annulée");

    private final String value;

    public static ReservationStatus getDefault() {
        return PLANNED;
    }
}
