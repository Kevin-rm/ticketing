package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.itu.ticketing.enums.ReservationStatus;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.getDefault();

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "reservation")
    private List<SeatReservation> seatReservations = new ArrayList<>();
}
