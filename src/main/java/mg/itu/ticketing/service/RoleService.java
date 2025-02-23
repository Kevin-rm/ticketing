package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mg.itu.ticketing.entity.Role;
import mg.matsd.javaframework.core.annotations.Component;
import mg.matsd.javaframework.core.annotations.Nullable;

@Component
public class RoleService {
    public static final String DEFAULT_ROLE_VALUE = "CUSTOMER";

    public Role getOrCreateDefault(final EntityManager entityManager) {
        Role role = getByValue(DEFAULT_ROLE_VALUE, entityManager);
        if (role == null) {
            role = new Role();
            role.setValue(DEFAULT_ROLE_VALUE);

            entityManager.persist(role);
        }

        return role;
    }

    @Nullable
    public Role getByValue(final String value, final EntityManager entityManager) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE UPPER(r.value) = UPPER(:value)", Role.class)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
