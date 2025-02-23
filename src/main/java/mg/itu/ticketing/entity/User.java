package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.matsd.javaframework.security.base.UserRole;

import java.util.List;

@Entity
@Table(name = "_user")
public class User implements mg.matsd.javaframework.security.base.User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(length = 75, nullable = false)
    private String firstname;

    @Getter
    @Setter
    @Column(length = 75, nullable = false)
    private String lastname;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private List<Role> userRoles;

    @Override
    public String getIdentifier() {
        return email;
    }

    @Override
    public List<? extends UserRole> getRoles() {
        return userRoles;
    }
}
