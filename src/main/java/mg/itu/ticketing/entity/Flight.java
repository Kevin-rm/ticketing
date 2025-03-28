package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private LocalDateTime departureTimestamp;

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
