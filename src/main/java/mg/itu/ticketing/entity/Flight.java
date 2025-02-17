package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private LocalDateTime departureTimestamp;

    @Setter
    @Column(nullable = false)
    private LocalDateTime arrivalTimestamp;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private City departureCity;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private City arrivalCity;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Plane plane;
}
