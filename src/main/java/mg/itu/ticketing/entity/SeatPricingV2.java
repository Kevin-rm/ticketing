package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
public class SeatPricingV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Setter
    @Column(nullable = false)
    private Integer availableSeatCount;

    @Setter
    @Column(nullable = false)
    private Integer seatCount; // The number of seats available at this price

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Seat seat;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Flight flight;

    @Setter
    @Column(nullable = false)
    private LocalDateTime deadline;

    @PrePersist
    public void prePersist() {
        availableSeatCount = seatCount;
    }
}
