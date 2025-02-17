package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Entity
public class SeatPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Seat seat;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Flight flight;
}
