package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"seat_id", "flight_id"}))
public class SeatPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @OneToOne(mappedBy = "seatPricing")
    private Discount discount;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Seat seat;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Flight flight;
}
