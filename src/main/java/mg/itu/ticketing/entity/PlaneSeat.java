package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"plane_id", "seat_type_id"}))
public class PlaneSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private Integer seatsCount;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Plane plane;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private SeatType seatType;
}
