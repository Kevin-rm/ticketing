package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
