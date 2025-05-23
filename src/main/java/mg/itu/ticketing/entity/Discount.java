package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal percentage;

    @Setter
    @Column(nullable = false)
    private Integer seatCount;

    @Setter
    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private SeatPricing seatPricing;
}
