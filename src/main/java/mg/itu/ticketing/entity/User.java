package mg.itu.ticketing.entity;

import jakarta.persistence.*;
import lombok.*;
import mg.matsd.javaframework.security.base.UserRole;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "_user")
public class User implements mg.matsd.javaframework.security.base.User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(length = 75, nullable = false)
    private String firstname;

    @Setter
    @Column(length = 75, nullable = false)
    private String lastname;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
<<<<<<< Updated upstream
    private List<Role> userRoles = new ArrayList<>();

    public User() {
        Role role = new Role();
        role.setValue("USER");

        userRoles.add(role);
    }
=======
    private List<Role> userRoles = List.of(Role.createDefault());
>>>>>>> Stashed changes

    @Override
    public String getIdentifier() {
        return email;
    }

    @Override
    public List<? extends UserRole> getRoles() {
        return userRoles;
    }
}
