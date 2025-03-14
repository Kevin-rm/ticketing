package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "_settings")
@DynamicInsert
@DynamicUpdate
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(nullable = false)
    private Integer minReservationHours;

    @Setter
    @Column(nullable = false)
    private Integer minCancellationHours;

    private LocalDateTime modifiedAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
}
