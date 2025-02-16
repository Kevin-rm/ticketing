package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import mg.matsd.javaframework.security.base.implementation.SimpleUserRole;

@Getter
@Entity
@Table(name = "_role")
public class Role extends SimpleUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 75, nullable = false)
    private String value;
}
