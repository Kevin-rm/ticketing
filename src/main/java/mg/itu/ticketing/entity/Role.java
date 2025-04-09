package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mg.matsd.javaframework.security.base.UserRole;

@Getter
@ToString
@Entity
@Table(name = "_role")
public class Role implements UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(length = 75, nullable = false)
    private String value;

    @Override
    public String value() {
        return value;
    }
}
