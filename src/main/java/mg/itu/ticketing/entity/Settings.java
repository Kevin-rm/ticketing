package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
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
    private Integer minReservationHours = 1;

    @Setter
    @Column(nullable = false)
    private Integer minCancellationHours = 1;

    @Column(precision = 5, scale = 2)
    private BigDecimal childDiscountPercentage = new BigDecimal("50.00");

    private LocalDateTime modifiedAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
}
