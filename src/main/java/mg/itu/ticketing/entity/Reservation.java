package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mg.itu.ticketing.enums.ReservationStatus;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@DynamicInsert
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.getDefault();

    private LocalDateTime timestamp;

    @Setter
    @Column(nullable = false)
    private Integer adultCount;

    @Setter
    @Column(nullable = false)
    private Integer childCount;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private SeatPricing seatPricing;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
