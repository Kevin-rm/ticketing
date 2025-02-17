package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"seat_pricing_id", "reservation_id"}))
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private Integer seatsCount;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private SeatPricing seatPricing;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Reservation reservation;
}
